package be.brickbit.lpm.catering.controller.dto;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.controller.dto.StockProductDto;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductMapper;

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
                source.getAvgConsumption(),
                source.getStockLevel(),
                source.getClearance(),
                source.getProductType());
    }
}
