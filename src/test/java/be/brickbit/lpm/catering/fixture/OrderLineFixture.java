package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;

public class OrderLineFixture {
    public static OrderLine getJupilerOrderLine(){
        OrderLine orderLine = new OrderLine();

        orderLine.setQuantity(1);
        orderLine.setProduct(ProductFixture.getJupiler());
        orderLine.setStatus(OrderStatus.CREATED);

        return orderLine;
    }

    public static OrderLine getPizzaOrderLine(){
        OrderLine orderLine = new OrderLine();

        orderLine.setQuantity(1);
        orderLine.setProduct(ProductFixture.getPizza());
        orderLine.setStatus(OrderStatus.CREATED);

        return orderLine;
    }
}
