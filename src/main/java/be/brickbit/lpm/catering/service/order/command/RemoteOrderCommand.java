package be.brickbit.lpm.catering.service.order.command;

import javax.validation.Valid;
import java.util.List;

public class RemoteOrderCommand {
    @Valid
    private List<OrderLineCommand> orderLines;

    public List<OrderLineCommand> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineCommand> orderLines) {
        this.orderLines = orderLines;
    }
}
