package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.AbstractRepoIT;
import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRepositoryIT extends AbstractRepoIT{

    @Autowired
    private OrderRepository orderRepository;

	@Test
	public void findByOrderLineId() throws Exception {
        Order order = OrderFixture.mutable();

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        Order result = orderRepository.findByOrderLinesId(order.getOrderLines().get(0).getId());
        assertThat(result).isEqualTo(order);
	}

    @Test
    public void findsOrdersByOrderLineStatus() throws Exception {
        Order order = OrderFixture.mutable();

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        List<Order> result = orderRepository.findDistinctByOrderLinesStatus(order.getOrderLines().get(0).getStatus());

        assertThat(result).contains(order);
    }

    @Test
    public void countsOrdersContainingProduct() throws Exception {
        Order order = OrderFixture.mutable();

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        Integer result = orderRepository.countByOrderLinesProductId(order.getOrderLines().get(0).getProduct().getId());

        assertThat(result).isEqualTo(1);
    }
}