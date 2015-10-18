package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.stockproduct.dto.StockProductDto;

public class StockProductDtoFixture {
    public static StockProductDto getStockProductDto(){
        return new StockProductDto(
                1L,
                "Jupiler 33cl",
                1,
                1,
                20,
                ClearanceType.PLUS_16,
                ProductType.DRINKS
        );
    }
}
