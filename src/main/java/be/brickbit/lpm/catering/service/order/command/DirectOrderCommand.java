package be.brickbit.lpm.catering.service.order.command;

import java.util.List;

public class DirectOrderCommand {
    private Long userId;
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
