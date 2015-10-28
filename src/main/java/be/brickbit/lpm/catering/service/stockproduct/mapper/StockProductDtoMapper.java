package be.brickbit.lpm.catering.service.stockproduct.mapper;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import be.brickbit.lpm.catering.service.stockproduct.dto.StockProductDto;
import org.springframework.stereotype.Component;

@Component
public class StockProductDtoMapper implements StockProductMapper<StockProductDto> {
    @Override
    public StockProductDto map(StockProduct source) {
        return new StockProductDto(
                source.getId(),
                source.getName(),
                source.getRemainingConsumptions(),
                source.getMaxConsumptions(),
                source.getStockLevel(),
                source.getClearance(),
                source.getProductType());
    }
}
