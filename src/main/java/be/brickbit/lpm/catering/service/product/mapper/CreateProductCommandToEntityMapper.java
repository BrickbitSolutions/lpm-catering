package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductPreparation;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.OptionalInt;
import java.util.stream.Collectors;

@Component
public class CreateProductCommandToEntityMapper implements Mapper<CreateProductCommand, Product> {

    @Autowired
    private ReceiptCommandToEntityMapper receiptCommandToEntityMapper;

    @Override
    public Product map(CreateProductCommand someCreateProductCommand) {
        Product product = new Product();

        product.setName(someCreateProductCommand.getName());
        product.setPrice(someCreateProductCommand.getPrice());
        product.setProductType(someCreateProductCommand.getProductType());
        product.setReceipt(
                someCreateProductCommand.getReceipt().stream().map(receiptCommandToEntityMapper::map).collect(Collectors.toList())
        );

        OptionalInt maxClearanceLevel = product.getReceipt().stream().mapToInt(value -> value.getStockProduct().getClearance().getClearanceLevel()).max();

        if(maxClearanceLevel.isPresent()){
            product.setClearance(ClearanceType.from(maxClearanceLevel.getAsInt()));
        }else{
            product.setClearance(ClearanceType.ANY);
        }

        product.setPreparation(createPreparation(someCreateProductCommand));
        product.setAvailable(false);

        return product;
    }

    private ProductPreparation createPreparation(CreateProductCommand someCreateProductCommand) {

        if(StringUtils.isEmpty(someCreateProductCommand.getQueueName())){
            return null;
        }

        ProductPreparation productPreparation = new ProductPreparation();

        productPreparation.setInstructions(someCreateProductCommand.getInstructions());
        productPreparation.setQueueName(someCreateProductCommand.getQueueName());
        productPreparation.setTimer(someCreateProductCommand.getTimerInMinutes());

        return productPreparation;
    }
}
