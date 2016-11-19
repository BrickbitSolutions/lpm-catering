package be.brickbit.lpm.catering.fixture;

import com.google.common.collect.Lists;

import be.brickbit.lpm.catering.domain.Order;

import java.time.LocalDateTime;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class OrderFixture {
    public static Order mutable(){
        Order order = new Order();

        order.setUserId(1L);
        order.setPlacedByUserId(1L);
        order.setTimestamp(LocalDateTime.now());
        order.setOrderLines(Lists.newArrayList(OrderLineFixture.getJupilerOrderLine(), OrderLineFixture.getPizzaOrderLine()));
        order.setComment(randomString());

        return order;
    }
}
