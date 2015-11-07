package be.brickbit.lpm.catering.service.order.command;

import java.util.List;

public class RemoteOrderCommand {
    private List<OrderLineCommand> orderLines;

    public List<OrderLineCommand> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineCommand> orderLines) {
        this.orderLines = orderLines;
    }
}
