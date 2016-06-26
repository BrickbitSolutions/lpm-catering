package be.brickbit.lpm.catering.service.order.command;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class DirectOrderCommand {
    @NotNull(message = "User id cannot be empty.")
    private Long userId;
    @Valid
    @NotNull(message = "Orderlines may not be null")
    @Size(min = 1, message = "Order must have orderlines")
    private List<OrderLineCommand> orderLines;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderLineCommand> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineCommand> orderLines) {
        this.orderLines = orderLines;
    }
}
