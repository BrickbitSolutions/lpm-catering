package be.brickbit.lpm.catering.service.order.command;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class RemoteOrderCommand {
    @Valid
    @NotNull(message = "Orderlines may not be null")
    @Size(min = 1, message = "Order must have orderlines")
    private List<OrderLineCommand> orderLines;

    public List<OrderLineCommand> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineCommand> orderLines) {
        this.orderLines = orderLines;
    }
}
