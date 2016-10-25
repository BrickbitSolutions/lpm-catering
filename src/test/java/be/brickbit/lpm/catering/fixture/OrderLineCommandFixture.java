package be.brickbit.lpm.catering.fixture;

import com.google.common.collect.Lists;

import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;

public class OrderLineCommandFixture {
    public static OrderLineCommand getOrderLineCommand(){
        OrderLineCommand command = new OrderLineCommand();

        command.setProductId(1L);
        command.setQuantity(1);

        return command;
    }

    public static OrderLineCommand getOrderLineCommand2(){
        OrderLineCommand command = new OrderLineCommand();

        command.setProductId(2L);
        command.setQuantity(1);

        return command;
    }
}
