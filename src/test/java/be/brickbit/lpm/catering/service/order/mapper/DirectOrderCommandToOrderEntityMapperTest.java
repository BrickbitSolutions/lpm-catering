package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.fixture.DirectOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.core.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DirectOrderCommandToOrderEntityMapperTest {
    @Mock
    private OrderLineCommandToEntityMapper orderLineCommandToEntityMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DirectOrderCommandToOrderEntityMapper mapper;

    @Test
    public void testMap() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

        User cateringAdmin = UserFixture.getCateringAdmin();
        OrderLine jupilerOrderLine = OrderLineFixture.getJupilerOrderLine();
        OrderLine pizzaOrderLine = OrderLineFixture.getPizzaOrderLine();

        when(userRepository.findOne(command.getUserId())).thenReturn(cateringAdmin);
        when(orderLineCommandToEntityMapper.map(command.getOrderLines().get(0))).thenReturn(jupilerOrderLine);
        when(orderLineCommandToEntityMapper.map(command.getOrderLines().get(1))).thenReturn(pizzaOrderLine);

        Order order = mapper.map(command);

        assertThat(order.getOrderLines()).hasSize(2);
        assertThat(order.getOrderLines().get(0)).isSameAs(jupilerOrderLine);
        assertThat(order.getOrderLines().get(1)).isSameAs(pizzaOrderLine);
        assertThat(order.getUserId()).isEqualTo(cateringAdmin.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testException() throws Exception {
        mapper.map(DirectOrderCommandFixture.getDirectOrderCommand());
    }
}