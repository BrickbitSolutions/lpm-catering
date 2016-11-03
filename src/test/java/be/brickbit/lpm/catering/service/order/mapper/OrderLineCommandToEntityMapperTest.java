package be.brickbit.lpm.catering.service.order.mapper;

import com.google.common.collect.Lists;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.OrderLineCommandFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@RunWith(MockitoJUnitRunner.class)
public class OrderLineCommandToEntityMapperTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private StockProductRepository stockProductRepository;

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
		assertThat(orderLine.getPricePerUnit()).isSameAs(product.getPrice());
		assertThat(orderLine.getQuantity()).isEqualTo(command.getQuantity());
		assertThat(orderLine.getStatus()).isEqualTo(OrderStatus.CREATED);
	}

	@Test
	public void testMapFaultProduct() throws Exception {
		expectedException.expect(ServiceException.class);
		expectedException.expectMessage("Invalid Product");

		mapper.map(OrderLineCommandFixture.getOrderLineCommand());
	}

	@Test
	public void throwsServiceExceptionWithInvalidSupplement() throws Exception {
		StockProduct stockProduct = StockProductFixture.getStockProductCola();
		Product product = ProductFixture.getPizza();
		OrderLineCommand command = OrderLineCommandFixture.getOrderLineCommand();
		command.setSupplements(Lists.newArrayList(randomLong()));

		when(productRepository.getOne(command.getProductId())).thenReturn(product);
		when(stockProductRepository.findOne(command.getSupplements().get(0))).thenReturn(stockProduct);

		expectedException.expect(ServiceException.class);
		expectedException.expectMessage(String.format(
				"'%s' is not a valid supplement for '%s'",
				stockProduct.getName(),
				product.getName()));

		mapper.map(command);
	}

	@Test
	public void throwsEntityNotFoundWhenStockProductNotFound() throws Exception {
		Long stockProductId = randomLong();
		Product product = ProductFixture.getPizza();
		OrderLineCommand command = OrderLineCommandFixture.getOrderLineCommand();
		command.setSupplements(Lists.newArrayList(stockProductId));

		when(productRepository.getOne(command.getProductId())).thenReturn(product);

		expectedException.expect(EntityNotFoundException.class);
		expectedException.expectMessage(String.format("Supplement #%d not found", stockProductId));

		mapper.map(command);
	}
}