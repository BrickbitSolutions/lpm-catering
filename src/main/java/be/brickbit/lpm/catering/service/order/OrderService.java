package be.brickbit.lpm.catering.service.order;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.mapper.DirectOrderCommandToOrderEntityMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderMapper;
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

    @Override
    @Transactional
    public <T> T placeDirectOrder(DirectOrderCommand command, OrderMapper<T> dtoMapper, User user) {
        Order order = directOrderCommandMapper.map(command);
        order.setPlacedByUser(user);
        orderRepository.save(order);
        return dtoMapper.map(order);
    }

    @Override
    public <T> T placeRemoteOrder(DirectOrderCommand command, OrderMapper<T> dtoMapper) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    protected OrderRepository getRepository() {
        return orderRepository;
    }
}
