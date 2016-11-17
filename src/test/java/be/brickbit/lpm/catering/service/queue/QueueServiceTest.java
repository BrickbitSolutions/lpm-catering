package be.brickbit.lpm.catering.service.queue;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.PreparationTaskFixture;
import be.brickbit.lpm.catering.repository.PreparationTaskRepository;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.QueueDtoMapper;
import org.assertj.core.util.Lists;
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
    private QueueDtoMapper mapper;

    @InjectMocks
    private QueueServiceImpl service;

    @Test
    //Should only queue tasks if there is a preparation defined for a product.
    public void testQueueAllTasks() throws Exception {
        Order order = OrderFixture.mutable();
        when(mapper.map(any(PreparationTask.class))).thenReturn(new QueueDto());

        Long preparationTasks = order.getOrderLines().stream().filter(orderLine -> orderLine.getProduct().getPreparation() != null).count();
        List<QueueDto> result = service.queueOrder(order, mapper);

        verify(preparationTaskRepository, times(preparationTasks.intValue())).save(any(PreparationTask.class));
        assertThat(result.size()).isEqualTo(preparationTasks.intValue());
    }

    @Test
    public void findAllTasks() throws Exception {
        final PreparationTask preparationTask = PreparationTaskFixture.mutable();
        List<PreparationTask> tasks = Lists.newArrayList(preparationTask);
        QueueDto dto = new QueueDto();
        String queueName = "queueName";

        when(preparationTaskRepository.findAllByQueueName(queueName)).thenReturn(tasks);
        when(mapper.map(preparationTask)).thenReturn(dto);

        assertThat(service.findAllTasks(queueName, mapper)).containsExactly(dto);
    }
}