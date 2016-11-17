package be.brickbit.lpm.catering.util;

import java.util.OptionalInt;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

public class OrderUtils {
    public static OrderStatus determineOrderStatus(Order order) {
        OptionalInt statusLevel = order.getOrderLines().stream().mapToInt(o -> o.getStatus().getStatusLevel()).min();
        if (statusLevel.isPresent()) {
            return OrderStatus.from(statusLevel.getAsInt());
        } else {
            throw new ServiceException("Invalid order status");
        }
    }
}
