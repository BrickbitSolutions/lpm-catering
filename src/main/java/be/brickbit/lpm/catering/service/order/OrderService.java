package be.brickbit.lpm.catering.service.order;

import java.util.List;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.service.order.mapper.OrderDtoMapper;
import be.brickbit.lpm.catering.service.stockflow.util.StockFlowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.mapper.DirectOrderCommandToOrderEntityMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderMapper;
import be.brickbit.lpm.catering.service.order.mapper.RemoteOrderCommandToEntityMapper;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.AbstractService;

@Service
public class OrderService extends AbstractService<Order> implements IOrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private StockProductRepository stockProductRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private DirectOrderCommandToOrderEntityMapper directOrderCommandMapper;

	@Autowired
	private RemoteOrderCommandToEntityMapper remoteOrderCommandToEntityMapper;

    @Autowired
    private OrderDtoMapper orderDtoMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

	@Override
	@Transactional
	public <T> T placeDirectOrder(DirectOrderCommand command, OrderMapper<T> dtoMapper, User user) {
        updateStockLevels(command.getOrderLines());
		Order order = directOrderCommandMapper.map(command);
		order.setPlacedByUserId(user.getId());

        checkValidOrder(order, user);
		setOrderLineStatus(order, OrderStatus.COMPLETED);

		orderRepository.save(order);
		return dtoMapper.map(order);
	}

    private void checkValidOrder(Order order, User user) {
        Long nrDisabledProducts =
                order.getOrderLines().stream()
                        .map(OrderLine::getProduct)
                        .filter(p -> !p.getAvailable())
                        .count();

        if(nrDisabledProducts > 0){
            throw new RuntimeException("Order contains disabled products!");
        }
    }

    private void updateStockLevels(List<OrderLineCommand> orderLines) {
		for (OrderLineCommand orderLine : orderLines) {
			Integer orderAmount = orderLine.getQuantity();
			Product product = productRepository.findOne(orderLine.getProductId());
			for (ProductReceiptLine receiptLine : product.getReceipt()) {
				StockProduct stockProductToUpdate = receiptLine.getStockProduct();
				Integer totalQuantity = receiptLine.getQuantity() * orderAmount;
                StockFlowUtil.calculateNewStockLevel(stockProductToUpdate, totalQuantity);
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
	@Transactional
	public <T> T placeRemoteOrder(RemoteOrderCommand command, OrderMapper<T> dtoMapper, User user) {
		updateStockLevels(command.getOrderLines());
		Order order = remoteOrderCommandToEntityMapper.map(command);
		order.setPlacedByUserId(user.getId());
		order.setUserId(user.getId());

        checkValidOrder(order, user);
        setOrderLineStatus(order, OrderStatus.READY);

		orderRepository.save(order);
        pushToTakeOutQueue(order);

		return dtoMapper.map(order);
	}

    @Override
	@Transactional(readOnly = true)
	public <T> T findOrderByOrderLineId(Long orderLineId, OrderMapper<T> dtoMapper) {
		return dtoMapper.map(orderRepository.findByOrderLinesId(orderLineId));
	}

	@Override
    public <T> List<T> findOrderByStatus(OrderStatus orderStatus, OrderMapper<T> dtoMapper) {
        return orderRepository.findAllByOrderLinesStatus(orderStatus).stream().map(dtoMapper::map).collect(Collectors.toList());
    }

    @Override
    public void processOrder(Long id) {
        Order order = orderRepository.findOne(id);

        order.getOrderLines().stream()
                .filter(orderLine -> orderLine.getStatus() == OrderStatus.READY)
                .forEach(orderLine -> orderLine.setStatus(OrderStatus.COMPLETED));

        orderRepository.save(order);
    }

    private void pushToTakeOutQueue(Order order){
        if(order.getOrderLines().stream().filter(orderLine -> orderLine.getStatus() == OrderStatus.READY).count() > 0){
            messagingTemplate.convertAndSend("/topic/zanzibar.queue", orderDtoMapper.map(order));
        }
    }

    @Override
	protected OrderRepository getRepository() {
		return orderRepository;
	}
}
