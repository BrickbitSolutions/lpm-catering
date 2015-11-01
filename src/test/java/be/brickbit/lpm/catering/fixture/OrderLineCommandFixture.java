package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;

public class OrderLineCommandFixture {
    public static OrderLineCommand getOrderLineCommand(){
        OrderLineCommand command = new OrderLineCommand();

        command.setProductId(1L);
        command.setQuanity(1);

        return command;
    }
}
