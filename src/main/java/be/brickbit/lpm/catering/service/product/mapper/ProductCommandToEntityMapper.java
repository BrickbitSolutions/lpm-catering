package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductPreparation;
import be.brickbit.lpm.catering.service.product.command.ProductCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.OptionalInt;
import java.util.stream.Collectors;

@Component
public class ProductCommandToEntityMapper implements Mapper<ProductCommand, Product> {

    @Autowired
    private ReceiptCommandToEntityMapper receiptCommandToEntityMapper;

    @Override
    public Product map(ProductCommand someProductCommand) {
        Product product = new Product();

        product.setName(someProductCommand.getName());
        product.setPrice(someProductCommand.getPrice());
        product.setProductType(someProductCommand.getProductType());
        product.setReceipt(
                someProductCommand.getReceipt().stream().map(receiptCommandToEntityMapper::map).collect(Collectors.toList())
        );

        OptionalInt maxClearanceLevel = product.getReceipt().stream().mapToInt(value -> value.getStockProduct().getClearance().getClearanceLevel()).max();

        if(maxClearanceLevel.isPresent()){
            product.setClearance(ClearanceType.from(maxClearanceLevel.getAsInt()));
        }else{
            product.setClearance(ClearanceType.ANY);
        }

        product.setPreparation(createPreparation(someProductCommand));
        product.setAvailable(false);

        return product;
    }

    private ProductPreparation createPreparation(ProductCommand someProductCommand) {

        if(StringUtils.isEmpty(someProductCommand.getQueueName())){
            return null;
        }

        ProductPreparation productPreparation = new ProductPreparation();

        productPreparation.setInstructions(someProductCommand.getInstructions());
        productPreparation.setQueueName(someProductCommand.getQueueName());
        productPreparation.setTimer(someProductCommand.getTimerInMinutes());

        return productPreparation;
    }
}
