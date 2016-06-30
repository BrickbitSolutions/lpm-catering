package be.brickbit.lpm.catering.service.order.command;

import java.util.List;

public class CreateOrderCommand {
    private Long userToken;
    List<OrderLineCommand> orderLines;
    private String comment;

    public Long getUserToken() {
        return userToken;
    }

    public void setUserToken(Long userToken) {
        this.userToken = userToken;
    }

    public List<OrderLineCommand> getOrderLins() {
        return orderLines;
    }

    public void setOrderLins(List<OrderLineCommand> orderLins) {
        this.orderLines = orderLins;
    }

    public List<OrderLineCommand> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineCommand> orderLines) {
        this.orderLines = orderLines;
    }
}
