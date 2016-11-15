package be.brickbit.lpm.catering.fixture;

import java.util.Arrays;

import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;

public class RemoteOrderCommandFixture {
    public static RemoteOrderCommand getRemoteOrderCommand() {
        return new RemoteOrderCommand(
                Arrays.asList(OrderLineCommandFixture.getOrderLineCommand(), OrderLineCommandFixture.getOrderLineCommand2()),
                "Do not burn this shit you twat >:(",
                null
        );
    }
}
