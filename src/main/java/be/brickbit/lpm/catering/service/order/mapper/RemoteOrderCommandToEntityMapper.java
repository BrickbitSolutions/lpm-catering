package be.brickbit.lpm.catering.service.order.mapper;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;

@Component
public class RemoteOrderCommandToEntityMapper implements Mapper<RemoteOrderCommand, Order> {

    @Autowired
    private OrderLineCommandToEntityMapper orderLineCommandToEntityMapper;

    @Override
    public Order map(RemoteOrderCommand remoteOrderCommand) {
        Order order = new Order();

        order.setTimestamp(LocalDateTime.now());
        order.setOrderLines(remoteOrderCommand.getOrderLines().stream().map(orderLineCommandToEntityMapper::map).collect(Collectors.toList()));
        order.setComment(remoteOrderCommand.getComment());

        return order;
    }
}