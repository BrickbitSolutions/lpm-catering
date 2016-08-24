package be.brickbit.lpm.catering.service.order.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.fixture.OrderLineCommandFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@RunWith(MockitoJUnitRunner.class)
public class OrderLineCommandToEntityMapperTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private OrderLineCommandToEntityMapper mapper;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testMap() throws Exception {
		OrderLineCommand command = OrderLineCommandFixture.getOrderLineCommand();
		Product product = ProductFixture.getJupiler();

		when(productRepository.getOne(command.getProductId())).thenReturn(product);

		OrderLine orderLine = mapper.map(command);

		assertThat(orderLine.getProduct()).isSameAs(product);
		assertThat(orderLine.getQuantity()).isEqualTo(command.getQuantity());
		assertThat(orderLine.getStatus()).isEqualTo(OrderStatus.CREATED);
	}

	@Test
	public void testMapFaultProduct() throws Exception {
		expectedException.expect(ServiceException.class);
		expectedException.expectMessage("Invalid Product");

		mapper.map(OrderLineCommandFixture.getOrderLineCommand());
	}
}