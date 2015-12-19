package be.brickbit.lpm.catering.service.stockflow;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.fixture.StockFlowCommandFixture;
import be.brickbit.lpm.catering.fixture.StockFlowDtoFixture;
import be.brickbit.lpm.catering.fixture.StockFlowFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.repository.StockFlowRepository;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowCommandToEntityMapper;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowDtoMapper;
import be.brickbit.lpm.catering.service.stockflow.util.StockFlowUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockFlowServiceTest {

    @Mock
    private StockFlowRepository stockFlowRepository;

    @Mock
    private StockFlowCommandToEntityMapper stockFlowCommandToEntityMapper;

    @Mock
    private StockFlowDtoMapper dtoMapper;

    @InjectMocks
    private StockFlowService stockFlowService;

    @Test
    public void testSave() throws Exception {
        StockFlowCommand command = StockFlowCommandFixture.getStockFlowCommand();
        StockFlow entity = StockFlowFixture.getStockFlow();
        StockFlowDto dto = StockFlowDtoFixture.getStockFlowDto();

        when(stockFlowCommandToEntityMapper.map(command)).thenReturn(entity);
        when(dtoMapper.map(entity)).thenReturn(dto);

        StockFlowDto result = stockFlowService.save(command, UserFixture.getCateringAdmin(), dtoMapper);

        assertThat(result).isSameAs(dto);

        verify(stockFlowRepository).save(entity);
    }

    @Test
    public void testProcessStockFlow() throws Exception {
        StockFlow entity = StockFlowFixture.getStockFlow();
        Integer originalStock = entity.getDetails().get(0).getStockProduct().getStockLevel();

        when(stockFlowRepository.findOne(1L)).thenReturn(entity);

        stockFlowService.processStockFlow(1L);

        assertThat(entity.getDetails().get(0).getStockProduct().getStockLevel()).isNotEqualTo(originalStock);

        verify(stockFlowRepository).save(entity);
    }
}