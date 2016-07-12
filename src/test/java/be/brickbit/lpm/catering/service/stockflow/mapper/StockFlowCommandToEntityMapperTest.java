package be.brickbit.lpm.catering.service.stockflow.mapper;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockflow.command.ProductClass;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;

@RunWith(MockitoJUnitRunner.class)
public class StockFlowCommandToEntityMapperTest {
	@Mock
	private StockProductRepository stockProductRepository;

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private StockFlowCommandToEntityMapper mapper;

	@Test
	public void testMapStockProduct() throws Exception {
		StockFlowCommand command = new StockFlowCommand();
		command.setProductId(randomLong());
		command.setProductClass(ProductClass.STOCKPRODUCT);
		command.setQuantity(randomInt());
		command.setStockFlowType(StockFlowType.PURCHASED);

		StockProduct stockProductCola = StockProductFixture.getStockProductCola();
		when(stockProductRepository.findOne(command.getProductId())).thenReturn(stockProductCola);

		StockFlow result = mapper.map(command);

		assertThat(result.getDetails().size()).isEqualTo(1);
		assertThat(result.getDetails().get(0).getQuantity()).isEqualTo(command.getQuantity());
		assertThat(result.getDetails().get(0).getStockProduct()).isSameAs(stockProductCola);
		assertThat(result.getStockFlowType()).isEqualTo(command.getStockFlowType());
		assertThat(result.getTimestamp()).isNotNull();
	}

	@Test
	public void testMapProduct() throws Exception {
		StockFlowCommand command = new StockFlowCommand();
		command.setProductId(randomLong());
		command.setProductClass(ProductClass.PRODUCT);
		command.setQuantity(randomInt());
		command.setStockFlowType(StockFlowType.PURCHASED);

		Product product = ProductFixture.getJupiler();
		when(productRepository.findOne(command.getProductId())).thenReturn(product);

		StockFlow result = mapper.map(command);

		assertThat(result.getDetails().size()).isEqualTo(product.getReceipt().size());
		assertThat(result.getStockFlowType()).isEqualTo(command.getStockFlowType());
		assertThat(result.getTimestamp()).isNotNull();
	}
}