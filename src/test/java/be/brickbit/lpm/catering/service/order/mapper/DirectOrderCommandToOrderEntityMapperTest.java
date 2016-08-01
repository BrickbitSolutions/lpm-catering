package be.brickbit.lpm.catering.service.order.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.core.repository.UserRepository;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@RunWith(MockitoJUnitRunner.class)
public class DirectOrderCommandToOrderEntityMapperTest {
	@Mock
	private OrderLineCommandToEntityMapper orderLineCommandToEntityMapper;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private DirectOrderCommandToOrderEntityMapper mapper;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testMap() throws Exception {
		DirectOrderCommand command = DirectOrderCommandFixture.getDirectOrderCommand();

		User cateringAdmin = UserFixture.getCateringAdmin();
		OrderLine jupilerOrderLine = OrderLineFixture.getJupilerOrderLine();
		OrderLine pizzaOrderLine = OrderLineFixture.getPizzaOrderLine();

		when(userRepository.findBySeatNumber(command.getSeatNumber())).thenReturn(Optional.of(cateringAdmin));
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

		when(userRepository.findBySeatNumber(directOrderCommand.getSeatNumber())).thenReturn(Optional.empty());

		mapper.map(directOrderCommand);
	}
}