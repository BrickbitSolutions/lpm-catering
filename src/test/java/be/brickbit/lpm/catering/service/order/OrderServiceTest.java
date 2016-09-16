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

import java.util.List;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.fixture.DirectOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.OrderDtoFixture;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.QueueDtoFixture;
import be.brickbit.lpm.catering.fixture.RemoteOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.service.order.mapper.DirectOrderCommandToOrderEntityMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderDtoMapper;
import be.brickbit.lpm.catering.service.order.mapper.RemoteOrderCommandToEntityMapper;
import be.brickbit.lpm.catering.service.queue.IQueueService;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.QueueDtoMapper;
import be.brickbit.lpm.catering.service.wallet.WalletService;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

import static be.brickbit.lpm.catering.domain.OrderStatus.COMPLETED;
import static be.brickbit.lpm.catering.domain.OrderStatus.IN_PROGRESS;
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
    private ProductRepository productRepository;

    @Mock
    private StockProductRepository stockProductRepository;

    @Mock
    private OrderDtoMapper dtoMapper;

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
        OrderDto orderDto = OrderDtoFixture.mutable();
        QueueDto queueDto = QueueDtoFixture.mutable();

        when(directOrderCommandMapper.map(command)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDto);
        when(productRepository.findOne(any(Long.class))).thenReturn(ProductFixture.getJupiler());
        when(queueService.queueOrder(order, queueDtoMapper)).thenReturn(Lists.newArrayList(queueDto));

        OrderDto result = orderService.placeDirectOrder(command, dtoMapper, UserFixture.mutablePrincipal());

        assertThat(result).isSameAs(orderDto);

        verify(messagingTemplate, times(1)).convertAndSend("/topic/kitchen.queue." + queueDto.getQueueName(), queueDto);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void refusesDirectOrderWithDisabledProducts() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

        Order order = OrderFixture.mutable();
        order.getOrderLines().get(0).getProduct().setAvailable(false);

        when(directOrderCommandMapper.map(command)).thenReturn(order);
        when(productRepository.findOne(any(Long.class))).thenReturn(ProductFixture.getPizza());

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Order contains disabled products!");

        orderService.placeDirectOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void refusesDirectOrderWithOutOfStockProducts() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

        Order order = OrderFixture.mutable();

        when(directOrderCommandMapper.map(command)).thenReturn(order);
        when(productRepository.findOne(any(Long.class))).thenReturn(ProductFixture.getPizza());

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Not enough 'Pizza' in stock to process order!");

        orderService.placeDirectOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void placesRemoteOrder() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();

        Order order = OrderFixture.mutable();
        OrderDto orderDto = OrderDtoFixture.mutable();
        QueueDto queueDto = QueueDtoFixture.mutable();

        when(remoteOrderCommandToEntityMapper.map(command)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDto);
        when(productRepository.findOne(1L)).thenReturn(ProductFixture.getJupiler());
        when(productRepository.findOne(2L)).thenReturn(ProductFixture.getPizza());
        when(queueService.queueOrder(order, queueDtoMapper)).thenReturn(Lists.newArrayList(queueDto));

        OrderDto result = orderService.placeRemoteOrder(command, dtoMapper, UserFixture.mutablePrincipal());

        verify(messagingTemplate).convertAndSend("/topic/kitchen.queue." + queueDto.getQueueName(), queueDto);
        verify(messagingTemplate).convertAndSend("/topic/zanzibar.queue", orderDto);
        assertThat(result).isSameAs(orderDto);
        verify(orderRepository).save(order);
    }

    @Test
    public void refusesRemoteOrderWithDisabledProducts() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();

        Order order = OrderFixture.mutable();
        order.getOrderLines().get(0).getProduct().setAvailable(false);

        when(remoteOrderCommandToEntityMapper.map(command)).thenReturn(order);
        when(productRepository.findOne(any(Long.class))).thenReturn(ProductFixture.getPizza());

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Order contains disabled products!");

        orderService.placeRemoteOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void refusesRemoteOrderWithOutOfStockProducts() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();

        Order order = OrderFixture.mutable();

        when(remoteOrderCommandToEntityMapper.map(command)).thenReturn(order);
        when(productRepository.findOne(any(Long.class))).thenReturn(ProductFixture.getPizza());

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Not enough 'Pizza' in stock to process order!");

        orderService.placeRemoteOrder(command, dtoMapper, UserFixture.mutablePrincipal());
    }

    @Test
    public void findsOrderByOrderLineID() throws Exception {
        Order order = OrderFixture.mutable();
        Long orderLineId = randomLong();
        OrderDto orderDto = OrderDtoFixture.mutable();

        when(orderRepository.findByOrderLinesId(orderLineId)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDto);

        OrderDto result = orderService.findOrderByOrderLineId(orderLineId, dtoMapper);

        assertThat(result).isSameAs(orderDto);
    }

    @Test
    public void findsOrdersByOrderStatus() throws Exception {
        List<Order> orders = Lists.newArrayList(OrderFixture.mutable());
        OrderStatus status = OrderStatus.READY;

        when(orderRepository.findDistinctByOrderLinesStatus(status)).thenReturn(orders);
        when(dtoMapper.map(any(Order.class))).thenReturn(OrderDtoFixture.mutable());

        List<OrderDto> result = orderService.findOrderByStatus(status, dtoMapper);

        assertThat(result).hasSameSizeAs(orders);
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
}