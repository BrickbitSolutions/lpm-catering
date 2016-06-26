package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.Arrays;

public class OrderFixture {
    public static Order getOrder(){
        Order order = new Order();

        order.setUserId(1L);
        order.setPlacedByUserId(1L);
        order.setTimestamp(LocalDateTime.now());
        order.setOrderLines(Arrays.asList(OrderLineFixture.getJupilerOrderLine(), OrderLineFixture.getPizzaOrderLine()));
        order.setOrderStatus(OrderStatus.QUEUED);

        return order;
    }
}
