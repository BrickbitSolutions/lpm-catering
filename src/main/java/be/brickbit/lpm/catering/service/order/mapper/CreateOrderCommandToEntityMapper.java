package be.brickbit.lpm.catering.service.order.mapper;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.service.order.command.CreateOrderCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;

@Component
public class CreateOrderCommandToEntityMapper implements Mapper<CreateOrderCommand, Order> {

    @Autowired
    private OrderLineCommandToEntityMapper orderLineCommandToEntityMapper;

    @Override
    public Order map(CreateOrderCommand orderCommand) {
        Order order = new Order();

        order.setTimestamp(LocalDateTime.now());
        order.setOrderLines(orderCommand.getOrderLines().stream().map(orderLineCommandToEntityMapper::map).collect(Collectors.toList()));
        order.setComment(orderCommand.getComment());

        return order;
    }
}