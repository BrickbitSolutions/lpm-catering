package be.brickbit.lpm.catering.service.order.mapper;

import java.math.BigDecimal;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.repository.UserRepository;

@Component
public class OrderDtoMapper implements OrderMapper<OrderDto> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderLineDtoMapper orderLineMapper;

	@Override
	public OrderDto map(Order order) {
		return new OrderDto(
				order.getId(),
				order.getOrderLines().stream().map(o -> o.getProduct().getPrice()).reduce(BigDecimal::add).get(),
				order.getTimestamp().format(DateUtils.getDateFormat()),
				getUsername(order.getUserId()),
				getOrderStatus(order),
				order.getOrderLines().stream().map(orderLineMapper::map).collect(Collectors.toList()),
                order.getComment());
	}

	private OrderStatus getOrderStatus(Order order) {
		OptionalInt statusLevel = order.getOrderLines().stream().mapToInt(o -> o.getStatus().getStatusLevel()).min();
		if (statusLevel.isPresent()) {
			return OrderStatus.from(statusLevel.getAsInt());
		} else {
			throw new RuntimeException("Invalid order status");
		}
	}

	private String getUsername(Long userId) {
		return userRepository.findOne(userId).getUsername();
	}
}
