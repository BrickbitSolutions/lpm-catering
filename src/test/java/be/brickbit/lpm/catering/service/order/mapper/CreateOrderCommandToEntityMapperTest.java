package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.fixture.CreateOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.service.order.command.CreateOrderCommand;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateOrderCommandToEntityMapperTest {

    @Mock
    private OrderLineCommandToEntityMapper orderLineCommandToEntityMapper;

    @InjectMocks
    private CreateOrderCommandToEntityMapper mapper;

    @Test
    public void testMap() throws Exception {
        CreateOrderCommand command = CreateOrderCommandFixture.getRemoteOrderCommand();

        OrderLine jupilerOrderLine = OrderLineFixture.getJupilerOrderLine();
        OrderLine pizzaOrderLine = OrderLineFixture.getPizzaOrderLine();

        when(orderLineCommandToEntityMapper.map(command.getOrderLines().get(0))).thenReturn(jupilerOrderLine);
        when(orderLineCommandToEntityMapper.map(command.getOrderLines().get(1))).thenReturn(pizzaOrderLine);

        Order order = mapper.map(command);

        assertThat(order.getOrderLines()).hasSize(2);
        assertThat(order.getOrderLines().get(0)).isSameAs(jupilerOrderLine);
        assertThat(order.getOrderLines().get(1)).isSameAs(pizzaOrderLine);
        assertThat(order.getComment()).isEqualTo(command.getComment());
    }
}