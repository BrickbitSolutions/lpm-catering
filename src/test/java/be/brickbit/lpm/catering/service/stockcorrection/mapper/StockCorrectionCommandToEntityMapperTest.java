package be.brickbit.lpm.catering.service.stockcorrection.mapper;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockCorrectionCommandFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockcorrection.command.StockCorrectionCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockCorrectionCommandToEntityMapperTest {

    @Mock
    private StockProductRepository stockProductRepository;

    @InjectMocks
    private StockCorrectionCommandToEntityMapper stockCorrectionMapper;

    @Test
    public void testMap() throws Exception {
        StockCorrectionCommand command = StockCorrectionCommandFixture.getNewStockCorrectionCommand();
        StockProduct product = StockProductFixture.getStockProduct();

        when(stockProductRepository.findOne(command.getStockProductId())).thenReturn(product);

        StockCorrection stockCorrection = stockCorrectionMapper.map(command);

        assertThat(stockCorrection.getStockProduct()).isSameAs(product);
        assertThat(stockCorrection.getQuantity()).isEqualTo(command.getQuantity());
        assertThat(stockCorrection.getTimestamp()).isNotNull();
        assertThat(stockCorrection.getMessage()).isEqualTo(command.getMessage());
    }
}