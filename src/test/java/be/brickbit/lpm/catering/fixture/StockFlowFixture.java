package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockFlowType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

public class StockFlowFixture {
    public static StockFlow getStockFlow(){
        StockFlow stockFlow = new StockFlow();

        stockFlow.setUserId(1L);
        stockFlow.setTimestamp(LocalDateTime.now());
        stockFlow.setIncluded(false);
        stockFlow.setStockFlowType(StockFlowType.PURCHASED);
        stockFlow.setDetails(Collections.singletonList(getStockFlowDetail()));

        return stockFlow;
    }

    public static StockFlowDetail getStockFlowDetail() {
        StockFlowDetail stockFlowDetail = new StockFlowDetail();

        stockFlowDetail.setStockProduct(StockProductFixture.getStockProductJupiler());
        stockFlowDetail.setQuantity(10);
        stockFlowDetail.setPrice(new BigDecimal(3.5));

        return stockFlowDetail;
    }
}
