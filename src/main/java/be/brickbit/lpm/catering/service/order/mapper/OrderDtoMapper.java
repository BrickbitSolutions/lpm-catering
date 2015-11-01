package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper implements OrderMapper<OrderDto> {

    @Override
    public OrderDto map(Order order) {
        return new OrderDto(
                order.getId(),
                order.getTotalPrice(),
                order.getTimestamp(),
                order.getUser().getUsername(),
                null
        );
    }
}
