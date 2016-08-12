package be.brickbit.lpm.catering.fixture;

import java.util.Arrays;

import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;

public class RemoteOrderCommandFixture {
	public static RemoteOrderCommand getRemoteOrderCommand(){
        return new RemoteOrderCommand()
                .setOrderLines(Arrays.asList(OrderLineCommandFixture.getOrderLineCommand(), OrderLineCommandFixture.getOrderLineCommand2()))
                .setComment("Do not burn this shit you twat >:(");
    }
}
