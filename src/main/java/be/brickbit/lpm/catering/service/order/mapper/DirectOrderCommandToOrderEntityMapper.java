package be.brickbit.lpm.catering.service.order.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.infrastructure.exception.ServiceException;
import be.brickbit.lpm.infrastructure.mapper.Mapper;

@Component
public class DirectOrderCommandToOrderEntityMapper implements Mapper<DirectOrderCommand, Order> {

    @Autowired
    private OrderLineCommandToEntityMapper orderLineCommandToEntityMapper;

    @Autowired
    private UserService userService;

    @Override
    public Order map(DirectOrderCommand directOrderCommand) {
        Optional<UserDetailsDto> user = Optional.ofNullable(userService.findOne(directOrderCommand.getUserId()));

        if (user.isPresent()) {
            Order order = new Order();

            order.setUserId(user.get().getId());
            order.setTimestamp(LocalDateTime.now());
            order.setOrderLines(directOrderCommand.getOrderLines().stream().map(orderLineCommandToEntityMapper::map).collect(Collectors.toList()));
            order.setComment(directOrderCommand.getComment());

            return order;
        } else {
            throw new ServiceException("Invalid user");
        }
    }
}
