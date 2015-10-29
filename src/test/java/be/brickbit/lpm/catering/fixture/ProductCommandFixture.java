package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.command.ProductCommand;

import java.math.BigDecimal;
import java.util.Arrays;

public class ProductCommandFixture {
    public static ProductCommand getProductCommand(){
        ProductCommand command = new ProductCommand();

        command.setProductType(ProductType.DRINKS);
        command.setName("Jupiler 33cl");
        command.setPrice(BigDecimal.ONE);
        command.setReceipt(Arrays.asList(
                ReceiptCommandFixture.getReceiptLineCommand(),
                ReceiptCommandFixture.getReceiptLineCommand2())
        );

        return command;
    }
}
