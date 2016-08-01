package be.brickbit.lpm.catering.service.order.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.fixture.OrderFixture;

public class PriceUtilTest {
	@Test
	public void calculateTotalPrice() throws Exception {
		Order order = OrderFixture.mutable();

		BigDecimal total = PriceUtil.calculateTotalPrice(order);
		BigDecimal expectedTotal = BigDecimal.ZERO;

		for (OrderLine orderLine : order.getOrderLines()) {
			expectedTotal = expectedTotal.add(orderLine.getProduct().getPrice().multiply(BigDecimal.valueOf(orderLine.getQuantity())));
		}

		assertThat(total).isEqualTo(expectedTotal);
	}
}