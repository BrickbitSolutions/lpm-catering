package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.AbstractRepoIT;
import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.PreparationTaskFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PreparationTaskRepositoryIT extends AbstractRepoIT {
    @Autowired
    private PreparationTaskRepository repository;

    @Test
    public void findAllByQueueName__In_Progress() throws Exception {
        PreparationTask preparationTask = PreparationTaskFixture.mutable();
        Order order = OrderFixture.mutable();
        preparationTask.setOrderLine(order.getOrderLines().get(1));
        preparationTask.setStatus(OrderStatus.QUEUED);

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order,
                preparationTask
        );

        List<PreparationTask> result = repository.findAllByQueueName(preparationTask.getOrderLine().getProduct()
                .getPreparation().getQueueName());

        assertThat(result).containsExactly(preparationTask);
    }

    @Test
    public void findAllByQueueName__Queued() throws Exception {
        PreparationTask preparationTask = PreparationTaskFixture.mutable();
        Order order = OrderFixture.mutable();
        preparationTask.setOrderLine(order.getOrderLines().get(1));
        preparationTask.setStatus(OrderStatus.IN_PROGRESS);

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order,
                preparationTask
        );

        List<PreparationTask> result = repository.findAllByQueueName(preparationTask.getOrderLine().getProduct()
                .getPreparation().getQueueName());

        assertThat(result).containsExactly(preparationTask);
    }

    @Test
    public void findAllByQueueName__NoValidStatus() throws Exception {
        PreparationTask preparationTask = PreparationTaskFixture.mutable();
        Order order = OrderFixture.mutable();
        preparationTask.setOrderLine(order.getOrderLines().get(1));
        preparationTask.setStatus(OrderStatus.COMPLETED);

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order,
                preparationTask
        );

        List<PreparationTask> result = repository.findAllByQueueName(preparationTask.getOrderLine().getProduct()
                .getPreparation().getQueueName());

        assertThat(result).isEmpty();
    }
}