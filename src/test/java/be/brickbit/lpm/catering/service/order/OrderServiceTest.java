package be.brickbit.lpm.catering.service.order;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.fixture.DirectOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.OrderDtoFixture;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.RemoteOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.service.order.mapper.DirectOrderCommandToOrderEntityMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderDtoMapper;
import be.brickbit.lpm.catering.service.order.mapper.RemoteOrderCommandToEntityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DirectOrderCommandToOrderEntityMapper directOrderCommandMapper;

    @Mock
    private RemoteOrderCommandToEntityMapper remoteOrderCommandToEntityMapper;

    @Mock
    private OrderDtoMapper dtoMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testPlaceDirectOrder() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

        Order order = OrderFixture.getOrder();
        OrderDto orderDto = OrderDtoFixture.getOrderDto();

        when(directOrderCommandMapper.map(command)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDto);

        OrderDto result = orderService.placeDirectOrder(command, dtoMapper, UserFixture.getCateringAdmin());

        assertThat(result).isSameAs(orderDto);
        verify(orderRepository).save(order);
    }

    @Test
    public void testPlaceRemoteOrder() throws Exception {
        RemoteOrderCommand command = RemoteOrderCommandFixture.getRemoteOrderCommand();

        Order order = OrderFixture.getOrder();
        OrderDto orderDto = OrderDtoFixture.getOrderDto();

        when(remoteOrderCommandToEntityMapper.map(command)).thenReturn(order);
        when(dtoMapper.map(order)).thenReturn(orderDto);

        OrderDto result = orderService.placeRemoteOrder(command, dtoMapper, UserFixture.getCateringAdmin());

        assertThat(result).isSameAs(orderDto);
        verify(orderRepository).save(order);
    }
}