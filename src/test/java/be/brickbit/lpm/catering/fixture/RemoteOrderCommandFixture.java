package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;

import java.util.Arrays;

public class RemoteOrderCommandFixture {
    public static RemoteOrderCommand getRemoteOrderCommand(){
        RemoteOrderCommand command = new RemoteOrderCommand();

        command.setOrderLines(Arrays.asList(OrderLineCommandFixture.getOrderLineCommand(), OrderLineCommandFixture.getOrderLineCommand2() ));

        return command;
    }
}
