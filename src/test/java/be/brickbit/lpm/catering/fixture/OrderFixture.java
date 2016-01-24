package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.Order;

import java.time.LocalDateTime;
import java.util.Arrays;

public class OrderFixture {
    public static Order getOrder(){
        Order order = new Order();

        order.setUserId(UserFixture.getCateringAdmin().getId());
        order.setPlacedByUserId(UserFixture.getCateringAdmin().getId());
        order.setTimestamp(LocalDateTime.now());
        order.setOrderLines(Arrays.asList(OrderLineFixture.getJupilerOrderLine(), OrderLineFixture.getPizzaOrderLine()));

        return order;
    }
}
