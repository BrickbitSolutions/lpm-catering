package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.service.product.command.ProductCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        return product;
    }
}
