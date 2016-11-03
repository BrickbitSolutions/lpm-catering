package be.brickbit.lpm.catering.service.order.util;

import be.brickbit.lpm.catering.domain.Order;

import java.math.BigDecimal;

public class PriceUtil {
    public static BigDecimal calculateTotalPrice(Order order){
        return order.getOrderLines().stream().map(o -> o.getPricePerUnit().multiply(BigDecimal.valueOf(o.getQuantity()))).reduce(BigDecimal::add).get();
    }
}
