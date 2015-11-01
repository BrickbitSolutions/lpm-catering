package be.brickbit.lpm.catering.service.order.command;

import java.util.List;

public class CreateOrderCommand {
    private Long userToken;
    List<OrderLineCommand> orderLins;

    public Long getUserToken() {
        return userToken;
    }

    public void setUserToken(Long userToken) {
        this.userToken = userToken;
    }

    public List<OrderLineCommand> getOrderLins() {
        return orderLins;
    }

    public void setOrderLins(List<OrderLineCommand> orderLins) {
        this.orderLins = orderLins;
    }
}
