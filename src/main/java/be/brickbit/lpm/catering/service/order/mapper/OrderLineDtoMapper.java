package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.service.order.dto.OrderLineDto;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class OrderLineDtoMapper implements Mapper<OrderLine, OrderLineDto>{
    @Override
    public OrderLineDto map(OrderLine orderLine) {
        return new OrderLineDto(
                orderLine.getQuantity(),
                orderLine.getProduct().getName(),
                orderLine.getStatus()
        );
    }
}
