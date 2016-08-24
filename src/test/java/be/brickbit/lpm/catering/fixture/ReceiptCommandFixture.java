package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.product.command.ReceiptLineCommand;

public class ReceiptCommandFixture {
    public static ReceiptLineCommand getReceiptLineCommand(){
        ReceiptLineCommand command = new ReceiptLineCommand();

        command.setQuantity(2);
        command.setStockProductId(1L);

        return command;
    }

    public static ReceiptLineCommand getReceiptLineCommand2(){
        ReceiptLineCommand command = new ReceiptLineCommand();

        command.setQuantity(2);
        command.setStockProductId(2L);

        return command;
    }
}
