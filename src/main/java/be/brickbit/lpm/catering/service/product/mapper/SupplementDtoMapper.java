package be.brickbit.lpm.catering.service.product.mapper;

import org.springframework.stereotype.Component;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.service.product.dto.SupplementDto;
import be.brickbit.lpm.infrastructure.mapper.Mapper;

@Component
public class SupplementDtoMapper implements Mapper<StockProduct, SupplementDto> {
    @Override
    public SupplementDto map(StockProduct stockProduct) {
        return new SupplementDto(
                stockProduct.getId(),
                stockProduct.getName()
        );
    }
}
