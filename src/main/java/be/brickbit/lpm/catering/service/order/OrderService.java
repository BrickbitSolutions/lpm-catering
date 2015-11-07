package be.brickbit.lpm.catering.service.order;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.mapper.DirectOrderCommandToOrderEntityMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderMapper;
import be.brickbit.lpm.catering.service.order.mapper.RemoteOrderCommandToEntityMapper;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService extends AbstractService<Order> implements IOrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DirectOrderCommandToOrderEntityMapper directOrderCommandMapper;

    @Autowired
    private RemoteOrderCommandToEntityMapper remoteOrderCommandToEntityMapper;

    @Override
    @Transactional
    public <T> T placeDirectOrder(DirectOrderCommand command, OrderMapper<T> dtoMapper, User user) {
        Order order = directOrderCommandMapper.map(command);
        order.setPlacedByUserId(user.getId());
        orderRepository.save(order);
        return dtoMapper.map(order);
    }

    @Override
    public <T> T placeRemoteOrder(RemoteOrderCommand command, OrderMapper<T> dtoMapper, User user) {
        Order order = remoteOrderCommandToEntityMapper.map(command);
        order.setPlacedByUserId(user.getId());
        order.setUserId(user.getId());
        orderRepository.save(order);
        return dtoMapper.map(order);
    }

    @Override
    protected OrderRepository getRepository() {
        return orderRepository;
    }
}
