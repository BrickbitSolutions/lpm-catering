package be.brickbit.lpm.catering.service.order.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.dto.OrderDetailDto;
import be.brickbit.lpm.catering.service.order.dto.ReservationDetailDto;
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
                order.getTimestamp().format(DateUtils.getDateTimeFormat()),
                user.getUsername(),
                user.getSeatNumber(),
                OrderUtils.determineOrderStatus(order),
                order.getOrderLines().stream().map(orderLineMapper::map).collect(Collectors.toList()),
                order.getComment(),
                getReservationDetails(order));
    }

    private ReservationDetailDto getReservationDetails(Order order) {
        if (order.getHoldUntil() == null) {
            return null;
        } else {
            return new ReservationDetailDto(
                    order.getHoldUntil().format(DateUtils.getDateFormat()),
                    isHoldExpired(order)
            );
        }
    }

    /**
     * Hold on a reservation expires when the holdUntil date is after or equal to today
     * @param order
     * @return a Boolean that flags if the hold is expired or not
     */
    private Boolean isHoldExpired(Order order) {
        return OrderUtils.determineOrderStatus(order) == OrderStatus.CREATED &&
                (order.getHoldUntil().isBefore(LocalDate.now()) ||
                        order.getHoldUntil().isEqual(LocalDate.now()));
    }
}
