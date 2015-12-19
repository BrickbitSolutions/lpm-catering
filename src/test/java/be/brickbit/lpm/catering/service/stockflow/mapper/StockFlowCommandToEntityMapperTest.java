package be.brickbit.lpm.catering.service.stockflow.mapper;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowDetailCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockFlowCommandToEntityMapperTest {
    @Mock
    private StockFlowDetailCommandToEntityMapper detailMapper;

    @InjectMocks
    private StockFlowCommandToEntityMapper mapper;

    @Test
    public void testMap() throws Exception {
        StockFlowCommand command = new StockFlowCommand();
        StockFlowDetailCommand stockFlowDetailCommand = new StockFlowDetailCommand();
        command.setStockFlowDetails(Collections.singletonList(stockFlowDetailCommand));
        command.setStockFlowType(StockFlowType.PURCHASED);

        when(detailMapper.map(stockFlowDetailCommand)).thenReturn(new StockFlowDetail());

        StockFlow result = mapper.map(command);

        assertThat(result.getDetails().size()).isEqualTo(command.getStockFlowDetails().size());
        assertThat(result.getStockFlowType()).isEqualTo(command.getStockFlowType());
        assertThat(result.getIncluded()).isFalse();
        assertThat(result.getTimestamp()).isNotNull();
    }
}