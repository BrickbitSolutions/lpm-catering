package be.brickbit.lpm.catering.service.order.mapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.fixture.DirectOrderCommandFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DirectOrderCommandToOrderEntityMapperTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private OrderLineCommandToEntityMapper orderLineCommandToEntityMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private DirectOrderCommandToOrderEntityMapper mapper;

    @Test
    public void testMap() throws Exception {
        DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

        UserDetailsDto cateringAdmin = UserFixture.mutable();
        OrderLine jupilerOrderLine = OrderLineFixture.getJupilerOrderLine();
        OrderLine pizzaOrderLine = OrderLineFixture.getPizzaOrderLine();

        when(userService.findOne(command.getUserId())).thenReturn(cateringAdmin);
        when(orderLineCommandToEntityMapper.map(command.getOrderLines().get(0))).thenReturn(jupilerOrderLine);
        when(orderLineCommandToEntityMapper.map(command.getOrderLines().get(1))).thenReturn(pizzaOrderLine);

        Order order = mapper.map(command);

        assertThat(order.getOrderLines()).hasSize(2);
        assertThat(order.getOrderLines().get(0)).isSameAs(jupilerOrderLine);
        assertThat(order.getOrderLines().get(1)).isSameAs(pizzaOrderLine);
        assertThat(order.getUserId()).isEqualTo(cateringAdmin.getId());
        assertThat(order.getComment()).isEqualTo(command.getComment());
    }

    @Test
    public void testMap__InvalidUser() throws Exception {
        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Invalid user");

        final DirectOrderCommand directOrderCommand = DirectOrderCommandFixture.getDirectOrderCommand();

        when(userService.findOne(directOrderCommand.getUserId())).thenReturn(null);

        mapper.map(directOrderCommand);
    }
}