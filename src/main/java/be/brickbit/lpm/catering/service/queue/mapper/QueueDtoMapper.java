package be.brickbit.lpm.catering.service.queue.mapper;

import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import org.springframework.stereotype.Component;

@Component
public class QueueDtoMapper implements QueueMapper<QueueDto> {
    @Override
    public QueueDto map(PreparationTask preparationTask) {
        return new QueueDto()
                .setTaskId(preparationTask.getId())
                .setDescription(getDescription(preparationTask))
                .setInstructions(preparationTask.getOrderLine().getProduct().getPreparation().getInstructions())
                .setDuration(preparationTask.getOrderLine().getProduct().getPreparation().getTimer())
                .setStartTime(preparationTask.getStartTime())
                .setQueueName(preparationTask.getOrderLine().getProduct().getPreparation().getQueueName());
    }

    private String getDescription(PreparationTask preparationTask) {
        return preparationTask.getOrderLine().getQuantity() + "x "
                +  preparationTask.getOrderLine().getProduct().getName();
    }
}
