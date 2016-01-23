package be.brickbit.lpm.catering.service.queue;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.repository.PreparationTaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QueueServiceTest {
    @Mock
    private PreparationTaskRepository preparationTaskRepository;

    @InjectMocks
    private QueueService service;

    @Test
    //Should only queue tasks if there is a preparation defined for a product.
    public void testQueueTasks() throws Exception {
        Order order = OrderFixture.getOrder();
        Long preparationTasks = order.getOrderLines().stream().filter(orderLine -> orderLine.getProduct().getPreparation() != null).count();
        service.queueTasks(order);
        verify(preparationTaskRepository, times(preparationTasks.intValue())).save(any(PreparationTask.class));
    }

    @Test
    public void testQueueTask() throws Exception {
        service.queueTask(OrderLineFixture.getJupilerOrderLine());
        verify(preparationTaskRepository).save(any(PreparationTask.class));
    }
}