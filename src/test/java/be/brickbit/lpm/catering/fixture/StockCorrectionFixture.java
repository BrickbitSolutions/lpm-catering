package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.StockCorrection;

import java.time.LocalDateTime;

public class StockCorrectionFixture {
    public static StockCorrection getStockCorrection() {
        StockCorrection stockCorrection = new StockCorrection();

        stockCorrection.setMessage("Test Correction");
        stockCorrection.setTimestamp(LocalDateTime.now());
        stockCorrection.setStockProduct(StockProductFixture.getStockProduct());
        stockCorrection.setQuantity(10);
        stockCorrection.setUserId(1L);

        return stockCorrection;
    }
}
