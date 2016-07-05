package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;

import java.util.Arrays;

public class DirectOrderCommandFixture {
    public static DirectOrderCommand getDirectOrderCommand(){

        return new DirectOrderCommand(
                1,
                Arrays.asList(OrderLineCommandFixture.getOrderLineCommand(), OrderLineCommandFixture.getOrderLineCommand2()),
                "Go out in style!"
        );
    }
}
