package be.brickbit.lpm.catering.service.stockproduct;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockProductDtoFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockproduct.command.SaveStockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.dto.StockProductDto;
import be.brickbit.lpm.catering.service.stockproduct.mapper.SaveStockProductMapper;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductDtoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockProductServiceTest {

    @Mock
    private StockProductRepository stockProductRepository;

    @Mock
    private StockProductDtoMapper mapper;

    @Mock
    private SaveStockProductMapper saveStockProductMapper;

    @InjectMocks
    private StockProductService stockProductService;


    @Test
    public void testFindAll() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProduct();
        List<StockProduct> stockProductList = Collections.singletonList(stockProduct);
        when(stockProductRepository.findAll()).thenReturn(stockProductList);
        StockProductDto dto = StockProductDtoFixture.getStockProductDto();
        when(mapper.map(any(StockProduct.class))).thenReturn(dto);

        List<StockProductDto> result = stockProductService.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isSameAs(dto);
    }

    @Test
    public void testFindAllByTypeAndClearance() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProduct();
        List<StockProduct> stockProductList = Collections.singletonList(stockProduct);
        when(stockProductRepository.findByProductTypeAndClearance(ProductType.DRINKS, ClearanceType.PLUS_16))
                .thenReturn(stockProductList);
        StockProductDto dto = StockProductDtoFixture.getStockProductDto();
        when(mapper.map(any(StockProduct.class))).thenReturn(dto);

        List<StockProductDto> result = stockProductService.findAllByTypeAndClearance(ProductType.DRINKS,
                ClearanceType.PLUS_16);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isSameAs(dto);
    }

    @Test
    public void testSaveStockProduct() throws Exception {
        SaveStockProductCommand command = new SaveStockProductCommand();
        StockProduct stockProduct = new StockProduct();

        when(saveStockProductMapper.map(command)).thenReturn(stockProduct);

        stockProductService.saveNewStockProduct(command);

        verify(stockProductRepository).save(stockProduct);
    }

    @Test
    public void testDelete() throws Exception {
        stockProductService.deleteStockProduct(1L);
        verify(stockProductRepository).delete(1L);
    }
}