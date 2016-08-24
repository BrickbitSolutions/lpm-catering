package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.Arrays;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class OrderFixture {
    public static Order mutable(){
        Order order = new Order();

        order.setUserId(1L);
        order.setPlacedByUserId(1L);
        order.setTimestamp(LocalDateTime.now());
        order.setOrderLines(Arrays.asList(OrderLineFixture.getJupilerOrderLine(), OrderLineFixture.getPizzaOrderLine()));
        order.setComment(randomString());

        return order;
    }
}
