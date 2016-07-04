package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderLinesId(Long orderLineId);
    List<Order> findAllByOrderLinesStatus(OrderStatus status);
}
