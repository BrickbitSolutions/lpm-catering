package be.brickbit.lpm.catering.service.stockproduct;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import be.brickbit.lpm.catering.service.stockproduct.command.EditStockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductMerger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockProductDtoFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.dto.StockProductDto;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductCommandToEntityMapper;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductDtoMapper;

@RunWith(MockitoJUnitRunner.class)
public class StockProductServiceTest {

	@Mock
	private StockProductRepository stockProductRepository;

	@Mock
	private StockProductDtoMapper mapper;

	@Mock
	private StockProductCommandToEntityMapper stockProductCommandToEntityMapper;

	@Mock
	private StockProductMerger stockProductMerger;

	@InjectMocks
	private StockProductService stockProductService;

	@Test
	public void testFindAllByTypeAndClearance() throws Exception {
		StockProduct stockProduct = StockProductFixture.getStockProductJupiler();
		List<StockProduct> stockProductList = Collections.singletonList(stockProduct);
		when(stockProductRepository.findByProductTypeAndClearance(ProductType.DRINKS, ClearanceType.PLUS_16))
				.thenReturn(stockProductList);
		StockProductDto dto = StockProductDtoFixture.getStockProductDto();
		when(mapper.map(any(StockProduct.class))).thenReturn(dto);

		List<StockProductDto> result = stockProductService.findAllByTypeAndClearance(ProductType.DRINKS,
				ClearanceType.PLUS_16, mapper);

		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0)).isSameAs(dto);
	}

	@Test
	public void testFindAllByType() throws Exception {
		StockProduct stockProduct = StockProductFixture.getStockProductJupiler();
		List<StockProduct> stockProductList = Collections.singletonList(stockProduct);
		when(stockProductRepository.findByProductType(ProductType.DRINKS))
				.thenReturn(stockProductList);
		StockProductDto dto = StockProductDtoFixture.getStockProductDto();
		when(mapper.map(any(StockProduct.class))).thenReturn(dto);

		List<StockProductDto> result = stockProductService.findAllByType(ProductType.DRINKS, mapper);

		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0)).isSameAs(dto);
	}

	@Test
	public void testSaveStockProduct() throws Exception {
		StockProductCommand command = new StockProductCommand();
		StockProduct stockProduct = new StockProduct();
		StockProductDto dto = StockProductDtoFixture.getStockProductDto();

		when(stockProductCommandToEntityMapper.map(command)).thenReturn(stockProduct);
		when(mapper.map(any(StockProduct.class))).thenReturn(dto);

		StockProductDto result = stockProductService.save(command, mapper);

		verify(stockProductRepository).save(stockProduct);
		assertThat(result).isSameAs(dto);
	}

	@Test
	public void testDelete() throws Exception {
		stockProductService.delete(1L);
		verify(stockProductRepository).delete(1L);
	}

	@Test
	public void testUpdate() throws Exception {
		Long stockProductId = randomLong();
		StockProduct stockProduct = StockProductFixture.getStockProductCola();
		when(stockProductRepository.findOne(stockProductId)).thenReturn(stockProduct);

		EditStockProductCommand command = new EditStockProductCommand();
		command.setClearance(ClearanceType.PLUS_21);
		command.setProductType(ProductType.FOOD);
		command.setName(randomString());

		stockProductService.updateStockProduct(stockProductId, command);

		verify(stockProductMerger, times(1)).merge(command, stockProduct);
	}

}