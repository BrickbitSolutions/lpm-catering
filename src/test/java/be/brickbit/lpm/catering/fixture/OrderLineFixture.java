package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.OrderLine;

public class OrderLineFixture {
    public static OrderLine getJupilerOrderLine(){
        OrderLine orderLine = new OrderLine();

        orderLine.setQuantity(1);
        orderLine.setProduct(ProductFixture.getProduct());

        return orderLine;
    }

    public static OrderLine getPizzaOrderLine(){
        OrderLine orderLine = new OrderLine();

        orderLine.setQuantity(1);
        orderLine.setProduct(ProductFixture.getProductPizza());

        return orderLine;
    }
}
