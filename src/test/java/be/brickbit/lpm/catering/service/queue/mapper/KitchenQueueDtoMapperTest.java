package be.brickbit.lpm.catering.service.queue.mapper;

import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.fixture.PreparationTaskFixture;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KitchenQueueDtoMapperTest {

    private QueueDtoMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new QueueDtoMapper();
    }

    @Test
    public void testMap() throws Exception {
        PreparationTask task = PreparationTaskFixture.mutable();

        QueueDto result = mapper.map(task);

        assertThat(result.getTaskId()).isEqualTo(task.getId());
        assertThat(result.getDescription()).isEqualTo(task.getOrderLine().getQuantity() + "x " + task.getOrderLine().getProduct().getName());
        assertThat(result.getDuration()).isEqualTo(task.getOrderLine().getProduct().getPreparation().getTimer());
        assertThat(result.getInstructions()).isEqualTo(task.getOrderLine().getProduct().getPreparation().getInstructions());
        assertThat(result.getQueueName()).isEqualTo(task.getOrderLine().getProduct().getPreparation().getQueueName());
        assertThat(result.getStartTime()).isEqualTo(task.getStartTime());
    }
}