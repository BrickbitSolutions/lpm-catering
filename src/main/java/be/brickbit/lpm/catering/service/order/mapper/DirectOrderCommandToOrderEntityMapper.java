package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.core.repository.UserRepository;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DirectOrderCommandToOrderEntityMapper implements Mapper<DirectOrderCommand, Order> {

    @Autowired
    private OrderLineCommandToEntityMapper orderLineCommandToEntityMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Order map(DirectOrderCommand directOrderCommand) {
        Optional<User> user = Optional.ofNullable(userRepository.findOne(directOrderCommand.getUserId()));

        if(user.isPresent()) {
            Order order = new Order();

            order.setUser(user.get());
            order.setTimestamp(LocalDateTime.now());
            order.setOrderLines(directOrderCommand.getOrderLines().stream().map(orderLineCommandToEntityMapper::map).collect(Collectors.toList()));
            order.setTotalPrice(order.getOrderLines().stream().map(o -> o.getProduct().getPrice()).reduce(BigDecimal::add).get());

            return order;
        }else{
            throw new EntityNotFoundException("Invalid user.");
        }
    }
}
