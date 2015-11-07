package be.brickbit.lpm.catering.service.order.command;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DirectOrderCommand {
    @NotNull(message = "User id cannot be empty.")
    private Long userId;
    @Valid
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
