package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.command.ProductCommand;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

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
        command.setEnableDirectQueueing(true);

        return command;
    }

    public static ProductCommand getProductCommandFood(){
        ProductCommand command = new ProductCommand();

        command.setProductType(ProductType.FOOD);
        command.setName("Pizza Margarita");
        command.setPrice(BigDecimal.TEN);
        command.setReceipt(Collections.singletonList(ReceiptCommandFixture.getReceiptLineCommand()));
        command.setQueueName("oven_queue");
        command.setTimerInMinutes(40);
        command.setInstructions("Make sure oven is heated to 180 degrees");

        return command;
    }
}
