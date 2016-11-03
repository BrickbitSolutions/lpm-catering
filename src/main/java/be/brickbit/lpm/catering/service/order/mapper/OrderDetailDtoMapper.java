package be.brickbit.lpm.catering.service.order.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.OptionalInt;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.dto.OrderDetailDto;
import be.brickbit.lpm.catering.service.order.util.PriceUtil;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@Component
public class OrderDetailDtoMapper implements OrderMapper<OrderDetailDto> {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderLineDtoMapper orderLineMapper;

    @Override
    public OrderDetailDto map(Order order) {
        UserDetailsDto user = userService.findOne(order.getUserId());

        return new OrderDetailDto(
                order.getId(),
                PriceUtil.calculateTotalPrice(order),
                order.getTimestamp().format(DateUtils.getDateFormat()),
                user.getUsername(),
                user.getSeatNumber(),
                getOrderStatus(order),
                order.getOrderLines().stream().map(orderLineMapper::map).collect(Collectors.toList()),
                order.getComment());
    }

    private OrderStatus getOrderStatus(Order order) {
        OptionalInt statusLevel = order.getOrderLines().stream().mapToInt(o -> o.getStatus().getStatusLevel()).min();
        if (statusLevel.isPresent()) {
            return OrderStatus.from(statusLevel.getAsInt());
        } else {
            throw new ServiceException("Invalid order status");
        }
    }
}
