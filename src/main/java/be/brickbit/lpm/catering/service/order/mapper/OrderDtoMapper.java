package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Component
public class OrderDtoMapper implements OrderMapper<OrderDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OrderDto map(Order order) {
        return new OrderDto(
                order.getId(),
                order.getOrderLines().stream().map(o -> o.getProduct().getPrice()).reduce(BigDecimal::add).get(),
                order.getTimestamp().format(DateUtils.getDateFormat()),
                getUsername(order.getUserId()),
                order.getOrderStatus()
        );
    }

    private String getUsername(Long userId) {return userRepository.findOne(userId).getUsername();}
}
