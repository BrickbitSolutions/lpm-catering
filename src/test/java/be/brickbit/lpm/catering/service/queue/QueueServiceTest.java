package be.brickbit.lpm.catering.service.queue;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.repository.PreparationTaskRepository;
import be.brickbit.lpm.catering.service.queue.dto.KitchenQueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.KitchenQueueDtoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueueServiceTest {
    @Mock
    private PreparationTaskRepository preparationTaskRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private QueueService service;

    @Test
    //Should only queue tasks if there is a preparation defined for a product.
    public void testQueueTasks() throws Exception {
        Order order = OrderFixture.getOrder();
        when(orderRepository.findOne(order.getId())).thenReturn(order);

        Long preparationTasks = order.getOrderLines().stream().filter(orderLine -> orderLine.getProduct().getPreparation() != null).count();
        List<KitchenQueueDto> result = service.queueTasks(order.getId(), new KitchenQueueDtoMapper());

        verify(preparationTaskRepository, times(preparationTasks.intValue())).save(any(PreparationTask.class));
        assertThat(result.size()).isEqualTo(preparationTasks.intValue());
    }
}