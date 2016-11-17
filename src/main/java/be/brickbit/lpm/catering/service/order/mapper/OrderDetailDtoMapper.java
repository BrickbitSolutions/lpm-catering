package be.brickbit.lpm.catering.service.order.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.service.order.dto.OrderDetailDto;
import be.brickbit.lpm.catering.service.order.util.PriceUtil;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.catering.util.OrderUtils;
import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;

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
                OrderUtils.determineOrderStatus(order),
                order.getOrderLines().stream().map(orderLineMapper::map).collect(Collectors.toList()),
                order.getComment());
    }
}
