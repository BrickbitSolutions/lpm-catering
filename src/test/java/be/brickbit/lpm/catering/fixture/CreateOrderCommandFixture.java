package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.order.command.CreateOrderCommand;

import java.util.Arrays;

public class CreateOrderCommandFixture {
    public static CreateOrderCommand getDirectOrderCommand(){

        return new CreateOrderCommand(
                1L,
                Arrays.asList(OrderLineCommandFixture.getOrderLineCommand(), OrderLineCommandFixture.getOrderLineCommand2()),
                "Go out in style!",
                null
        );
    }

    public static CreateOrderCommand getRemoteOrderCommand() {
        return new CreateOrderCommand(
                null,
                Arrays.asList(OrderLineCommandFixture.getOrderLineCommand(), OrderLineCommandFixture.getOrderLineCommand2()),
                "Do not burn this shit you twat >:(",
                null
        );
    }
}
