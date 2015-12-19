package be.brickbit.lpm.catering.service.stockflow.util;

import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.fixture.StockFlowFixture;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StockFlowUtilTest {

    @Test
    public void testCalculateNewStockCorrection() throws Exception {
        StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
        Integer result = StockFlowUtil.calculateNewStock(detail, StockFlowType.CORRECTION);
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void testCalculateNewStockLoss() throws Exception {
        StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
        Integer result = StockFlowUtil.calculateNewStock(detail, StockFlowType.LOSS);
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void testCalculateNewStockReturned() throws Exception {
        StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
        Integer result = StockFlowUtil.calculateNewStock(detail, StockFlowType.RETURNED);
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void testCalculateNewStockSold() throws Exception {
        StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
        Integer result = StockFlowUtil.calculateNewStock(detail, StockFlowType.SOLD);
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void testCalculateNewStockPurchased() throws Exception {
        StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
        Integer result = StockFlowUtil.calculateNewStock(detail, StockFlowType.PURCHASED);
        assertThat(result).isEqualTo(30);
    }
}