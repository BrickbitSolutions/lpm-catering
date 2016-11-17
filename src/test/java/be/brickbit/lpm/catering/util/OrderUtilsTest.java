package be.brickbit.lpm.catering.util;

import com.google.common.collect.Lists;

import org.junit.Test;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderUtilsTest {
    @Test
    public void returnsLowestOrderLineStatus() throws Exception {
        Order order = OrderFixture.mutable();
        OrderLine orderLine = OrderLineFixture.getPizzaOrderLine();
        orderLine.setStatus(OrderStatus.QUEUED);
        OrderLine orderLine1 = OrderLineFixture.getJupilerOrderLine();
        orderLine1.setStatus(OrderStatus.READY);
        order.setOrderLines(Lists.newArrayList(orderLine, orderLine1));

        assertThat(OrderUtils.determineOrderStatus(order)).isEqualTo(OrderStatus.QUEUED);
    }
}