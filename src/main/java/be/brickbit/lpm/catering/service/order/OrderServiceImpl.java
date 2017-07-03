package be.brickbit.lpm.catering.service.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.brickbit.lpm.catering.controller.dto.QueueDto;
import be.brickbit.lpm.catering.controller.mapper.OrderDetailDtoMapper;
import be.brickbit.lpm.catering.controller.mapper.QueueDtoMapper;
import be.brickbit.lpm.catering.domain.*;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.repository.PreparationTaskRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.order.command.CreateOrderCommand;
import be.brickbit.lpm.catering.service.order.mapper.CreateOrderCommandToEntityMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderMapper;
import be.brickbit.lpm.catering.service.order.util.PriceUtil;
import be.brickbit.lpm.catering.service.queue.QueueService;
import be.brickbit.lpm.catering.service.stockflow.util.StockFlowUtil;
import be.brickbit.lpm.catering.service.wallet.WalletService;
import be.brickbit.lpm.catering.util.OrderUtils;
import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;
import be.brickbit.lpm.infrastructure.AbstractService;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@Service
public class OrderServiceImpl extends AbstractService<Order> implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private StockProductRepository stockProductRepository;

	@Autowired
	private CreateOrderCommandToEntityMapper createOrderCommandToEntityMapper;

	@Autowired
	private OrderDetailDtoMapper orderDtoMapper;

	@Autowired
	private QueueService queueService;

	@Autowired
	private QueueDtoMapper queueDtoMapper;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private WalletService walletService;

	@Autowired
	private UserService userService;

	@Autowired
	private PreparationTaskRepository taskRepository;

	@Override
	@Transactional
	public <T> T createOrder(CreateOrderCommand command, OrderMapper<T> dtoMapper, UserPrincipalDto
			user) {
		Boolean isOrderForAuthenticatedUser = command.getUserId() == null || command.getUserId().equals(user.getId());

		Order order = createOrderCommandToEntityMapper.map(command);
		order.setPlacedByUserId(user.getId());

		if (isOrderForAuthenticatedUser) {
			order.setUserId(user.getId());
		} else {
			order.setUserId(command.getUserId());
		}

		checkValidOrder(order);

		if (command.getHoldUntil() == null) {
			handleOrder(order, isOrderForAuthenticatedUser ? OrderStatus.READY : OrderStatus.COMPLETED);
			if (isOrderForAuthenticatedUser) {
				pushToTakeOutQueue(order);
			}
		} else {
			createReservation(order, command.getHoldUntil());
		}

		return dtoMapper.map(order);
	}

	@Override
	@Transactional
	public void handleReservation(Long id) {
		Order order = orderRepository.findOne(id);

		if (order.getHoldUntil() == null) {
			throw new ServiceException("Order is not a reservation.");
		}

		if (LocalDate.now().isBefore(order.getHoldUntil())) {
			throw new ServiceException("Reservation cannot be handled yet.");
		}

		if (OrderUtils.determineOrderStatus(order) == OrderStatus.CREATED) {
			handleOrder(order, OrderStatus.READY);
		} else {
			throw new ServiceException("Reservation is already being handled.");
		}
	}

	private void createReservation(Order order, LocalDate holdUntil) {
		order.setHoldUntil(holdUntil);
		orderRepository.save(order);
	}

	private void handleOrder(Order order, OrderStatus statusAfterHandling) {
		updateStockLevels(order.getOrderLines());
		setOrderLineStatus(order, statusAfterHandling);

		orderRepository.save(order);
		queueService.queueOrder(order, queueDtoMapper).forEach(this::pushToKitchenQueue);
	}

	private void checkValidOrder(Order order) {
		UserDetailsDto user = userService.findOne(order.getUserId());

		order.getOrderLines().stream().map(OrderLine::getProduct)
				.forEach(product -> {
					if (!product.getAvailable()) {
						throw new ServiceException("Order contains disabled products!");
					}

					if (product.getClearance().getClearanceLevel() > user.getAge()) {
						throw new ServiceException("Order contains products who are lawfully " +
								"forbidden for the current user.");
					}

					if (product.getReservationOnly() && order.getHoldUntil() == null) {
						throw new ServiceException("Order containing reservation only products " +
								"must have a hold until date!");
					}
				});

		//If this doesn't throw an exception, user has sufficient funds.
		walletService.substractAmount(order.getUserId(), PriceUtil.calculateTotalPrice(order));
	}

	private void updateStockLevels(List<OrderLine> orderLines) {
		for (OrderLine orderLine : orderLines) {
			Integer orderAmount = orderLine.getQuantity();
			Product product = orderLine.getProduct();
			for (ProductReceiptLine receiptLine : product.getReceipt()) {
				StockProduct stockProductToUpdate = receiptLine.getStockProduct();
				Integer totalQuantity = receiptLine.getQuantity() * orderAmount;
				StockFlowUtil.calculateNewStockLevel(stockProductToUpdate, totalQuantity * -1);
				stockProductRepository.save(stockProductToUpdate);
			}
		}
	}

	private void setOrderLineStatus(Order order, OrderStatus orderStatus) {
		order.getOrderLines().stream()
				.filter(line -> line.getProduct().getPreparation() == null)
				.forEach(line -> line.setStatus(orderStatus));
	}

	@Override
	@Transactional(readOnly = true)
	public <T> T findOrderByOrderLineId(Long orderLineId, OrderMapper<T> dtoMapper) {
		return dtoMapper.map(orderRepository.findByOrderLinesId(orderLineId));
	}

	@Override
	public <T> List<T> findOrderByStatus(OrderStatus orderStatus, OrderMapper<T> dtoMapper) {
		return orderRepository.findDistinctByOrderLinesStatus(orderStatus).stream().map(dtoMapper::map).collect(Collectors.toList());
	}

	@Override
	public <T> List<T> findByUserId(Long userId, OrderMapper<T> dtoMapper) {
		return orderRepository.findByUserId(userId).stream().map(dtoMapper::map).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void processOrder(Long id) {
		Order order = orderRepository.findOne(id);

		order.getOrderLines().stream()
				.filter(orderLine -> orderLine.getStatus() == OrderStatus.READY)
				.forEach(orderLine -> orderLine.setStatus(OrderStatus.COMPLETED));

		orderRepository.save(order);
	}

	@Override
	public void notifyReady(Long id) {
		Order order = orderRepository.findOne(id);

		userService.notify(order.getUserId(), "Your order (#%s) is (partially) ready and waiting" +
				" for you at the catering!");
	}

	@Transactional(readOnly = true)
	@Override
	public <T> T findByTaskId(Long taskId, OrderMapper<T> dtoMapper) {
		Optional<PreparationTask> task = Optional.ofNullable(taskRepository.findOne(taskId));

		if(task.isPresent()){
			return findOrderByOrderLineId(task.get().getOrderLine().getId(), dtoMapper);
		}else{
			throw new ServiceException(String.format("Invalid Task ID (#%s)", taskId));
		}
	}

	private void pushToTakeOutQueue(Order order) {
		if (order.getOrderLines().stream().filter(orderLine -> orderLine.getStatus() == OrderStatus.READY).count() > 0) {
			messagingTemplate.convertAndSend("/topic/zanzibar.queue", orderDtoMapper.map(order));
		}
	}

	private void pushToKitchenQueue(QueueDto orderTask) {
		messagingTemplate.convertAndSend("/topic/kitchen.queue." + orderTask.getQueueName(), orderTask);
	}

	@Override
	protected OrderRepository getRepository() {
		return orderRepository;
	}
}
