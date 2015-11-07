package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;

import java.util.Arrays;

public class DirectOrderCommandFixture {
    public static DirectOrderCommand getDirectOrderCommand(){
        DirectOrderCommand command = new DirectOrderCommand();

        command.setUserId(1L);
        command.setOrderLines(Arrays.asList(OrderLineCommandFixture.getOrderLineCommand(), OrderLineCommandFixture.getOrderLineCommand2()));

        return command;
    }
}
