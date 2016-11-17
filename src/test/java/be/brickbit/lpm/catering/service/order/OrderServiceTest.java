package be.brickbit.lpm.catering.service.order;

import com.google.common.collect.Lists;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDate;
import java.util.List;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.DirectOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.OrderDtoFixture;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.ProductReceiptLineFixture;
import be.brickbit.lpm.catering.fixture.QueueDtoFixture;
import be.brickbit.lpm.catering.fixture.RemoteOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.dto.OrderDetailDto;
import be.brickbit.lpm.catering.service.order.mapper.DirectOrderCommandToOrderEntityMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderDetailDtoMapper;
import be.brickbit.lpm.catering.service.order.mapper.RemoteOrderCommandToEntityMapper;
import be.brickbit.lpm.catering.service.queue.IQueueService;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.QueueDtoMapper;
import be.brickbit.lpm.catering.service.wallet.WalletService;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

import static be.brickbit.lpm.catering.domain.OrderStatus.COMPLETED;
import static be.brickbit.lpm.catering.domain.OrderStatus.CREATED;
import static be.brickbit.lpm.catering.domain.OrderStatus.IN_PROGRESS;
import static be.brickbit.lpm.catering.domain.OrderStatus.QUEUED;
import static be.brickbit.lpm.catering.domain.OrderStatus.READY;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLocalDate;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DirectOrderCommandToOrderEntityMapper directOrderCommandMapper;

    @Mock
    private RemoteOrderCommandToEntityMapper remoteOrderCommandToEntityMapper;

    @Mock
    private StockProductRepository stockProductRepository;

    @Mock
    private OrderDetailDtoMapper dtoMapper;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private IQueueService queueService;

    @Mock
    private QueueDtoMapper queueDtoMapper;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private OrderService orderService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void placesDirectOrder() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

        Order order = OrderFixture.mutable();
        OrderDetailDto orderDetailDto = OrderDtoFixture.mutable();
        QueueDto queueDto = QueueDtoFixture.mutable();

        when(directOrderCommandMapper.map(command)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDetailDto);
        when(queueService.queueOrder(order, queueDtoMapper)).thenReturn(Lists.newArrayList(queueDto));

        OrderDetailDto result = orderService.placeDirectOrder(command, dtoMapper, UserFixture.mutablePrincipal());

        assertThat(result).isSameAs(orderDetailDto);

        verify(messagingTemplate, times(1)).convertAndSend("/topic/kitchen.queue." + queueDto.getQueueName(), queueDto);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void refusesDirectOrderWithDisabledProducts() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

        Order order = OrderFixture.mutable();
        order.getOrderLines().get(0).getProduct().setAvailable(false);

        when(directOrderCommandMapper.map(command)).thenReturn(order);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Order contains disabled products!");

        orderService.placeDirectOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void refusesDirectOrderWithOutOfStockProducts() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

        //Set Stock on 0
        StockProduct stockProduct = StockProductFixture.getStockProductPizza();
        stockProduct.setStockLevel(0);
        Product product = ProductFixture.getPizza();
        ProductReceiptLine receiptLine = ProductReceiptLineFixture.getPizza();
        receiptLine.setStockProduct(stockProduct);
        product.setReceipt(Lists.newArrayList(receiptLine));

        //Order a pizza
        Order order = OrderFixture.mutable();

        OrderLine pizzaLine = OrderLineFixture.getPizzaOrderLine();
        pizzaLine.setQuantity(1);
        pizzaLine.setProduct(product);
        pizzaLine.setPricePerUnit(product.getPrice());

        order.setOrderLines(Lists.newArrayList(pizzaLine));

        //Expect error
        when(directOrderCommandMapper.map(command)).thenReturn(order);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Not enough 'Pizza' in stock to process order!");

        orderService.placeDirectOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void refusesDirectOrderWithReservationOnlyProductAndNoHoldUntilDate() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();
        command.setHoldUntil(null);

        Order order = OrderFixture.mutable();
        order.getOrderLines().get(0).getProduct().setReservationOnly(true);

        when(directOrderCommandMapper.map(command)).thenReturn(order);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Order containing reservation only products " +
                "must have a hold until date!");

        orderService.placeDirectOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void placesRemoteOrder() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();

        Order order = OrderFixture.mutable();
        OrderDetailDto orderDetailDto = OrderDtoFixture.mutable();
        QueueDto queueDto = QueueDtoFixture.mutable();

        when(remoteOrderCommandToEntityMapper.map(command)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDetailDto);
        when(queueService.queueOrder(order, queueDtoMapper)).thenReturn(Lists.newArrayList(queueDto));

        OrderDetailDto result = orderService.placeRemoteOrder(command, dtoMapper, UserFixture.mutablePrincipal());

        verify(messagingTemplate).convertAndSend("/topic/kitchen.queue." + queueDto.getQueueName(), queueDto);
        verify(messagingTemplate).convertAndSend("/topic/zanzibar.queue", orderDetailDto);
        assertThat(result).isSameAs(orderDetailDto);
        verify(orderRepository).save(order);
    }

    @Test
    public void refusesRemoteOrderWithDisabledProducts() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();

        Order order = OrderFixture.mutable();
        order.getOrderLines().get(0).getProduct().setAvailable(false);

        when(remoteOrderCommandToEntityMapper.map(command)).thenReturn(order);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Order contains disabled products!");

        orderService.placeRemoteOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void refusesRemoteOrderWithOutOfStockProducts() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();

        //Set Stock on 0
        StockProduct stockProduct = StockProductFixture.getStockProductPizza();
        stockProduct.setStockLevel(0);
        Product product = ProductFixture.getPizza();
        ProductReceiptLine receiptLine = ProductReceiptLineFixture.getPizza();
        receiptLine.setStockProduct(stockProduct);
        product.setReceipt(Lists.newArrayList(receiptLine));

        //Order a pizza
        Order order = OrderFixture.mutable();

        OrderLine pizzaLine = OrderLineFixture.getPizzaOrderLine();
        pizzaLine.setQuantity(1);
        pizzaLine.setProduct(product);
        pizzaLine.setPricePerUnit(product.getPrice());

        order.setOrderLines(Lists.newArrayList(pizzaLine));

        when(remoteOrderCommandToEntityMapper.map(command)).thenReturn(order);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Not enough 'Pizza' in stock to process order!");

        orderService.placeRemoteOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void refusesRemoteOrderWithReservationOnlyProductAndNoHoldUntilDate() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();
        command.setHoldUntil(null);

        Order order = OrderFixture.mutable();
        order.getOrderLines().get(0).getProduct().setReservationOnly(true);

        when(remoteOrderCommandToEntityMapper.map(command)).thenReturn(order);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Order containing reservation only products " +
                "must have a hold until date!");

        orderService.placeRemoteOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void findsOrderByOrderLineID() throws Exception {
        Order order = OrderFixture.mutable();
        Long orderLineId = randomLong();
        OrderDetailDto orderDetailDto = OrderDtoFixture.mutable();

        when(orderRepository.findByOrderLinesId(orderLineId)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDetailDto);

        OrderDetailDto result = orderService.findOrderByOrderLineId(orderLineId, dtoMapper);

        assertThat(result).isSameAs(orderDetailDto);
    }

    @Test
    public void findsOrdersByOrderStatus() throws Exception {
        List<Order> orders = Lists.newArrayList(OrderFixture.mutable());
        OrderStatus status = OrderStatus.READY;

        when(orderRepository.findDistinctByOrderLinesStatus(status)).thenReturn(orders);
        when(dtoMapper.map(any(Order.class))).thenReturn(OrderDtoFixture.mutable());

        List<OrderDetailDto> result = orderService.findOrderByStatus(status, dtoMapper);

        assertThat(result).hasSameSizeAs(orders);
    }

    @Test
    public void findsOrdersByUserId() throws Exception {
        List<Order> orders = Lists.newArrayList(OrderFixture.mutable());
        Long userId = 1L;

        when(orderRepository.findByUserId(userId)).thenReturn(orders);
        when(dtoMapper.map(any(Order.class))).thenReturn(OrderDtoFixture.mutable());

        List<OrderDetailDto> result = orderService.findByUserId(userId, dtoMapper);

        assertThat(result).hasSameSizeAs(orders);
    }

    @Test
    public void handleReservation() throws Exception {
        Order order = OrderFixture.mutable();
        order.setHoldUntil(LocalDate.now());

        OrderLine orderLineFood = OrderLineFixture.getPizzaOrderLine();
        orderLineFood.setStatus(OrderStatus.CREATED);
        OrderLine orderLineDrinks = OrderLineFixture.getJupilerOrderLine();
        orderLineDrinks.setStatus(CREATED);

        order.setOrderLines(Lists.newArrayList(orderLineFood, orderLineDrinks));
        QueueDto queueDto = QueueDtoFixture.mutable();

        Long orderId = randomLong();

        when(orderRepository.findOne(orderId)).thenReturn(order);
        when(queueService.queueOrder(order, queueDtoMapper)).thenReturn(Lists.newArrayList(queueDto));

        orderService.handleReservation(orderId);

        verify(queueService, times(1)).queueOrder(order, queueDtoMapper);
        verify(orderRepository, times(1)).save(order);

        assertThat(order.getOrderLines().get(1).getStatus()).isEqualTo(READY);
    }

    @Test
    public void throwsServiceExceptionWhenOrderIsNotReservation() throws Exception {
        Order order = OrderFixture.mutable();
        order.setHoldUntil(null);
        OrderLine orderLineFood = OrderLineFixture.getPizzaOrderLine();
        orderLineFood.setStatus(OrderStatus.CREATED);
        order.setOrderLines(Lists.newArrayList(orderLineFood));

        Long orderId = randomLong();

        when(orderRepository.findOne(orderId)).thenReturn(order);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Order is not a reservation.");

        orderService.handleReservation(orderId);
    }

    @Test
    public void throwsServiceExceptionWhenOrderIsAlreadyBeingHandled() throws Exception {
        Order order = OrderFixture.mutable();
        order.setHoldUntil(LocalDate.now());
        OrderLine orderLineFood = OrderLineFixture.getPizzaOrderLine();
        orderLineFood.setStatus(OrderStatus.QUEUED);
        order.setOrderLines(Lists.newArrayList(orderLineFood));

        Long orderId = randomLong();

        when(orderRepository.findOne(orderId)).thenReturn(order);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Reservation is already being handled.");

        orderService.handleReservation(orderId);
    }

    @Test
    public void throwsServiceExceptionWhenHolUntilIsNotExpiredYet() throws Exception {
        Order order = OrderFixture.mutable();
        order.setHoldUntil(LocalDate.now().plusDays(1));

        Long orderId = randomLong();

        when(orderRepository.findOne(orderId)).thenReturn(order);

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Reservation cannot be handled yet.");

        orderService.handleReservation(orderId);
    }

    @Test
    public void processesOrderToCompleted() throws Exception {
        Order order = OrderFixture.mutable();
        OrderLine orderLine = OrderLineFixture.getPizzaOrderLine();
        orderLine.setStatus(OrderStatus.READY);
        order.setOrderLines(Lists.newArrayList(orderLine));

        Long orderId = randomLong();

        when(orderRepository.findOne(orderId)).thenReturn(order);

        orderService.processOrder(orderId);

        assertThat(order.getOrderLines().get(0).getStatus()).isEqualTo(COMPLETED);
    }

    @Test
    public void processesOrderAndLeavesNonReadyOrdersAsIs() throws Exception {
        Order order = OrderFixture.mutable();
        OrderLine orderLine = OrderLineFixture.getPizzaOrderLine();
        orderLine.setStatus(OrderStatus.IN_PROGRESS);
        order.setOrderLines(Lists.newArrayList(orderLine));

        Long orderId = randomLong();

        when(orderRepository.findOne(orderId)).thenReturn(order);

        orderService.processOrder(orderId);

        assertThat(order.getOrderLines().get(0).getStatus()).isEqualTo(IN_PROGRESS);
    }

    @Test
    public void createsDirectOrderReservation() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();
        command.setHoldUntil(randomLocalDate());

        Order order = OrderFixture.mutable();
        OrderDetailDto orderDetailDto = OrderDtoFixture.mutable();

        when(directOrderCommandMapper.map(command)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDetailDto);

        orderService.placeDirectOrder(command, dtoMapper, UserFixture.mutablePrincipal());

        assertThat(order.getHoldUntil()).isEqualTo(command.getHoldUntil());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void createsRemoteOrderReservation() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();
        command.setHoldUntil(randomLocalDate());

        Order order = OrderFixture.mutable();
        OrderDetailDto orderDetailDto = OrderDtoFixture.mutable();

        when(remoteOrderCommandToEntityMapper.map(command)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDetailDto);

        orderService.placeRemoteOrder(command, dtoMapper, UserFixture.mutablePrincipal());

        assertThat(order.getHoldUntil()).isEqualTo(command.getHoldUntil());
        verify(orderRepository, times(1)).save(order);
    }
}