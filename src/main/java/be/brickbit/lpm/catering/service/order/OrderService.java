package be.brickbit.lpm.catering.service.order;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import be.brickbit.lpm.catering.controller.dto.OrderDetailDto;
import be.brickbit.lpm.catering.controller.mapper.OrderDetailDtoMapper;
import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.command.CreateOrderCommand;
import be.brickbit.lpm.catering.service.order.mapper.OrderMapper;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;
import be.brickbit.lpm.infrastructure.Service;

public interface OrderService extends Service<Order> {
    <T> T createOrder(CreateOrderCommand command, OrderMapper<T> dtoMapper, UserPrincipalDto user);

    void handleReservation(Long id);

    <T> T findOrderByOrderLineId(Long orderLineId, OrderMapper<T> dtoMapper);

    <T> List<T> findOrderByStatus(OrderStatus orderStatus, OrderMapper<T> dtoMapper);

    <T> List<T> findByUserId(Long userId, OrderMapper<T> dtoMapper);

    void processOrder(Long id);

    void notifyReady(Long id);

    <T> T findByTaskId(Long taskId, OrderMapper<T> dtoMapper);
}
