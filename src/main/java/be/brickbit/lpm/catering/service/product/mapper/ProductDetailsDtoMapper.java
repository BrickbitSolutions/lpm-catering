package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.service.product.dto.ProductDetailsDto;
import be.brickbit.lpm.catering.service.product.dto.ReceiptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDetailsDtoMapper implements ProductMapper<ProductDetailsDto> {

    @Autowired
    private ReceiptDtoMapper receiptDtoMapper;

    @Override
    public ProductDetailsDto map(Product someProduct) {
        List<ReceiptDto> receiptDtos = someProduct.getReceipt().stream().map(receiptDtoMapper::map).collect(Collectors.toList());

        if(someProduct.getPreparation() == null || StringUtils.isEmpty(someProduct.getPreparation().getQueueName())){
            return new ProductDetailsDto(receiptDtos);
        }else{
            return new ProductDetailsDto(
                    someProduct.getPreparation().getQueueName(),
                    someProduct.getPreparation().getInstructions(),
                    someProduct.getPreparation().getTimer(),
                    receiptDtos
            );
        }
    }
}
