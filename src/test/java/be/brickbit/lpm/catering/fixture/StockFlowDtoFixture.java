package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.service.product.dto.ProductDetailsDto;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDetailDto;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

public class StockFlowDtoFixture {
    public static StockFlowDto getStockFlowDto(){
        return new StockFlowDto(
                1L,
                "jay",
                StockFlowType.PURCHASED,
                false,
                LocalDateTime.now(),
                Collections.singletonList(new StockFlowDetailDto("Jupiler 33cl", 10L, new BigDecimal(3.5)))
        );
    }
}
