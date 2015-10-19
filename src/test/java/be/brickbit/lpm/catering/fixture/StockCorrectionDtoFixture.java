package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;

import java.time.LocalDateTime;

public class StockCorrectionDtoFixture {
    public static StockCorrectionDto getStockCorrectionDto() {
        return new StockCorrectionDto(
                "Jupiler 33cl",
                3,
                LocalDateTime.now(),
                "Jay"
        );
    }
}
