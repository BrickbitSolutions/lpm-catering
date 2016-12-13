package be.brickbit.lpm.catering.service.product.mapper;

import com.google.common.collect.Lists;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.service.product.dto.ProductDetailsDto;
import be.brickbit.lpm.catering.service.product.dto.ReceiptDto;
import be.brickbit.lpm.catering.service.product.dto.SupplementDto;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDetailsDtoMapper implements ProductMapper<ProductDetailsDto> {

    @Autowired
    private ReceiptDtoMapper receiptDtoMapper;

    @Autowired
    private SupplementDtoMapper supplementDtoMapper;

    @Override
    public ProductDetailsDto map(Product someProduct) {
        List<ReceiptDto> receiptDtos = someProduct.getReceipt().stream().map(receiptDtoMapper::map).collect(Collectors.toList());

        if(someProduct.getPreparation() == null || StringUtils.isEmpty(someProduct.getPreparation().getQueueName())){
            return new ProductDetailsDto(
                    receiptDtos
            );
        }else{
            return new ProductDetailsDto(
                    someProduct.getPreparation().getQueueName(),
                    someProduct.getPreparation().getInstructions(),
                    calculateTimerInMinutes(someProduct.getPreparation().getTimer()),
                    receiptDtos
            );
        }
    }

    private Integer calculateTimerInMinutes(Integer timer) {
        if(timer != null){
            return timer / 60;
        }else{
            return null;
        }
    }
}
