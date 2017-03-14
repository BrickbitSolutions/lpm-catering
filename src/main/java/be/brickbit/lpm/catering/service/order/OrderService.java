package be.brickbit.lpm.catering.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.mapper.DirectOrderCommandToOrderEntityMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderDetailDtoMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderMapper;
import be.brickbit.lpm.catering.service.order.mapper.RemoteOrderCommandToEntityMapper;
import be.brickbit.lpm.catering.service.order.util.PriceUtil;
import be.brickbit.lpm.catering.service.queue.QueueService;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.QueueDtoMapper;
import be.brickbit.lpm.catering.service.stockflow.util.StockFlowUtil;
import be.brickbit.lpm.catering.service.wallet.WalletService;
import be.brickbit.lpm.catering.util.OrderUtils;
import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;
import be.brickbit.lpm.infrastructure.AbstractService;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@Service
public class OrderService extends AbstractService<Order> implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockProductRepository stockProductRepository;

    @Autowired
    private DirectOrderCommandToOrderEntityMapper directOrderCommandMapper;

    @Autowired
    private RemoteOrderCommandToEntityMapper remoteOrderCommandToEntityMapper;

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

    @Override
    @Transactional
    public <T> T placeDirectOrder(DirectOrderCommand command, OrderMapper<T> dtoMapper, Long userId) {
        Order order = directOrderCommandMapper.map(command);
        order.setPlacedByUserId(userId);

        checkValidOrder(order);

        if (command.getHoldUntil() == null) {
            handleOrder(order, OrderStatus.COMPLETED);
        } else {
            createReservation(order, command.getHoldUntil());
        }

        return dtoMapper.map(order);
    }

    @Override
    @Transactional
    public <T> T placeRemoteOrder(RemoteOrderCommand command, OrderMapper<T> dtoMapper, Long userId) {
        Order order = remoteOrderCommandToEntityMapper.map(command);
        order.setPlacedByUserId(userId);
        order.setUserId(userId);

        checkValidOrder(order);

        if (command.getHoldUntil() == null) {
            handleOrder(order, OrderStatus.READY);
        } else {
            createReservation(order, command.getHoldUntil());
        }

        pushToTakeOutQueue(order);

        return dtoMapper.map(order);
    }

    @Override
    @Transactional
    public void handleReservation(Long id) {
        Order order = orderRepository.findOne(id);

        if(order.getHoldUntil() == null){
            throw new ServiceException("Order is not a reservation.");
        }

        if(LocalDate.now().isBefore(order.getHoldUntil())){
            throw new ServiceException("Reservation cannot be handled yet.");
        }

        if(OrderUtils.determineOrderStatus(order) == OrderStatus.CREATED){
            handleOrder(order, OrderStatus.READY);
        }else{
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
                    if(!product.getAvailable()){
                        throw new ServiceException("Order contains disabled products!");
                    }

                    if(product.getClearance().getClearanceLevel() > user.getAge()){
                        throw new ServiceException("Order contains products who are lawfully " +
                                "forbidden for the current user.");
                    }

                    if(product.getReservationOnly() && order.getHoldUntil() == null){
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
