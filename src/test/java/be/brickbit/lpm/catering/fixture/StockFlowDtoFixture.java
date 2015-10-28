package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockFlowDtoFixture {
    public static StockFlowDto getStockFlowDto(){
        return new StockFlowDto(
                1L,
                10,
                new BigDecimal(3.5),
                "jay",
                StockFlowType.PURCHASED,
                false,
                LocalDateTime.now(),
                "Jupiler 33cl"
        );
    }
}
