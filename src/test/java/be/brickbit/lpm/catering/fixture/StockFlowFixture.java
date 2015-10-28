package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockFlowFixture {
    public static StockFlow getStockFlow(){
        StockFlow stockFlow = new StockFlow();

        stockFlow.setId(1L);
        stockFlow.setQuantity(10);
        stockFlow.setStockProduct(StockProductFixture.getStockProduct());
        stockFlow.setUserId(1L);
        stockFlow.setTimestamp(LocalDateTime.now());
        stockFlow.setIncluded(false);
        stockFlow.setPricePerUnit(new BigDecimal(3.5));
        stockFlow.setStockFlowType(StockFlowType.PURCHASED);

        return stockFlow;
    }
}
