package be.brickbit.lpm.catering.service.order;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.mapper.OrderMapper;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.Service;

import java.util.List;

public interface IOrderService extends Service<Order> {
    <T> T placeDirectOrder(DirectOrderCommand command, OrderMapper<T> dtoMapper, User user);
    <T> T placeRemoteOrder(RemoteOrderCommand command, OrderMapper<T> dtoMapper, User user);
    <T> T findOrderByOrderLineId(Long orderLineId, OrderMapper<T> dtoMapper);
    <T> List<T> findOrderByStatus(OrderStatus orderStatus, OrderMapper<T> dtoMapper);
    void processOrder(Long id);
}
