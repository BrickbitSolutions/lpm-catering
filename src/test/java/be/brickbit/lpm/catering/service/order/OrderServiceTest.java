package be.brickbit.lpm.catering.service.order;

import com.google.common.collect.Lists;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.fixture.DirectOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.OrderDtoFixture;
import be.brickbit.lpm.catering.fixture.OrderFixture;
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

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testPlaceDirectOrder() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

        Order order = OrderFixture.mutable();
        OrderDto orderDto = OrderDtoFixture.getOrderDto();
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
    public void testPlaceRemoteOrder() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();

        Order order = OrderFixture.mutable();
        OrderDto orderDto = OrderDtoFixture.getOrderDto();
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
}