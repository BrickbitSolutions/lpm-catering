package be.brickbit.lpm.catering.fixture;

import com.google.common.collect.Lists;

import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.StockProduct;

public class OrderLineFixture {
    public static OrderLine getJupilerOrderLine(){
        OrderLine orderLine = new OrderLine();
        Product product = ProductFixture.getJupiler();

        orderLine.setQuantity(2);
        orderLine.setProduct(product);
        orderLine.setPricePerUnit(product.getPrice());
        orderLine.setStatus(OrderStatus.CREATED);

        return orderLine;
    }

    public static OrderLine getPizzaOrderLine(){
        OrderLine orderLine = new OrderLine();
        Product product = ProductFixture.getPizza();

        orderLine.setQuantity(1);
        orderLine.setProduct(product);
        orderLine.setPricePerUnit(product.getPrice());
        orderLine.setStatus(OrderStatus.CREATED);

        return orderLine;
    }
}
