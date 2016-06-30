package be.brickbit.lpm.catering.service.queue.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.PreparationTaskFixture;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KitchenQueueDtoMapperTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private QueueDtoMapper mapper;

    @Test
    public void testMap() throws Exception {
        PreparationTask task = PreparationTaskFixture.mutable();
        Order order = OrderFixture.getOrder();

        when(orderRepository.findByOrderLinesId(any(Long.class))).thenReturn(order);

        QueueDto result = mapper.map(task);

        assertThat(result.getTaskId()).isEqualTo(task.getId());
        assertThat(result.getDescription()).isEqualTo(task.getOrderLine().getQuantity() + "x " + task.getOrderLine().getProduct().getName());
        assertThat(result.getDuration()).isEqualTo(task.getOrderLine().getProduct().getPreparation().getTimer());
        assertThat(result.getInstructions()).isEqualTo(task.getOrderLine().getProduct().getPreparation().getInstructions());
        assertThat(result.getQueueName()).isEqualTo(task.getOrderLine().getProduct().getPreparation().getQueueName());
        assertThat(result.getStartTime()).isEqualTo(task.getStartTime());
        assertThat(result.getComment()).isEqualTo(order.getComment());
    }

    @Test
    public void maps_invalidOrder() throws Exception {
        PreparationTask task = PreparationTaskFixture.mutable();

        when(orderRepository.findByOrderLinesId(any(Long.class))).thenReturn(null);

        QueueDto result = mapper.map(task);

        assertThat(result.getComment()).isNullOrEmpty();
    }
}