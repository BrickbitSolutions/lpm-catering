package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.AbstractRepoIT;
import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRepositoryTest extends AbstractRepoIT{

    @Autowired
    private OrderRepository orderRepository;

	@Test
	public void findByOrderLineId() throws Exception {
        Order order = OrderFixture.getOrder();

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
}