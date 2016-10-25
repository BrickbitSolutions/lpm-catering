package be.brickbit.lpm.catering.service.product.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductPreparation;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;

@Component
public class CreateProductCommandToEntityMapper implements Mapper<CreateProductCommand, Product> {

    @Autowired
    private ReceiptCommandToEntityMapper receiptCommandToEntityMapper;

    @Autowired
    private StockProductRepository stockProductRepository;

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

        if (maxClearanceLevel.isPresent()) {
            product.setClearance(ClearanceType.from(maxClearanceLevel.getAsInt()));
        } else {
            product.setClearance(ClearanceType.ANY);
        }

        if (CollectionUtils.isNotEmpty(someCreateProductCommand.getSupplements())) {
            product.setSupplements(
                    someCreateProductCommand.getSupplements().stream()
                            .map(this::findStockProduct)
                            .collect(Collectors.toList())
            );
        }

        product.setAvgConsumption(0);
        product.setPreparation(createPreparation(someCreateProductCommand));
        product.setAvailable(false);

        return product;
    }

    private ProductPreparation createPreparation(CreateProductCommand someCreateProductCommand) {

        if (StringUtils.isEmpty(someCreateProductCommand.getQueueName())) {
            return null;
        }

        ProductPreparation productPreparation = new ProductPreparation();

        productPreparation.setInstructions(someCreateProductCommand.getInstructions());
        productPreparation.setQueueName(someCreateProductCommand.getQueueName());
        productPreparation.setTimer(someCreateProductCommand.getTimerInMinutes());

        return productPreparation;
    }

    private StockProduct findStockProduct(Long id) {
        return Optional.ofNullable(stockProductRepository.findOne(id))
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Stock product #%d was not found", id)
                ));
    }
}
