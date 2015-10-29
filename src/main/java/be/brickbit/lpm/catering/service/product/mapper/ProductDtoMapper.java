package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.service.product.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductDtoMapper implements ProductMapper<ProductDto> {

    @Autowired
    private ReceiptDtoMapper receiptDtoMapper;

    @Override
    public ProductDto map(Product someProduct) {
        return new ProductDto(
                someProduct.getId(),
                someProduct.getName(),
                someProduct.getPrice(),
                someProduct.getProductType(),
                someProduct.getClearance(),
                someProduct.getReceipt().stream().map(receiptDtoMapper::map).collect(Collectors.toList())
        );
    }
}
