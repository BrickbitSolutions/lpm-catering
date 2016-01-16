package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.service.product.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper implements ProductMapper<ProductDto> {

    @Override
    public ProductDto map(Product someProduct) {
        return new ProductDto(
                someProduct.getId(),
                someProduct.getName(),
                someProduct.getPrice(),
                someProduct.getProductType(),
                someProduct.getClearance()
        );
    }
}
