package be.brickbit.lpm.catering.service.stockflow.mapper;

import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowDetailCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockFlowDetailCommandToEntityMapperTest {

    @Mock
    private StockProductRepository stockProductRepository;

    @InjectMocks
    private StockFlowDetailCommandToEntityMapper mapper;

    @Test
    public void testMap() throws Exception {
        StockFlowDetailCommand stockFlowDetail = new StockFlowDetailCommand();
        stockFlowDetail.setStockProductId(1L);
        stockFlowDetail.setQuantity(5L);
        stockFlowDetail.setPricePerUnit(BigDecimal.TEN);

        StockProduct stockProduct = StockProductFixture.getStockProduct();
        when(stockProductRepository.findOne(stockFlowDetail.getStockProductId())).thenReturn(stockProduct);

        StockFlowDetail result = mapper.map(stockFlowDetail);

        assertThat(result.getPrice()).isEqualTo(stockFlowDetail.getPricePerUnit());
        assertThat(result.getQuantity()).isEqualTo(stockFlowDetail.getQuantity());
        assertThat(result.getStockProduct()).isSameAs(stockProduct);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testInvalidProduct() throws Exception {
        mapper.map(new StockFlowDetailCommand());
    }
}