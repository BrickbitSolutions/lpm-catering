package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderLinesId(Long orderLineId);
}
