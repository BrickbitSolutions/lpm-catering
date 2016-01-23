package be.brickbit.lpm.catering.service.queue.mapper;

import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.service.queue.dto.KitchenQueueDto;
import org.springframework.stereotype.Component;

@Component
public class KitchenQueueDtoMapper implements QueueMapper<KitchenQueueDto> {
    @Override
    public KitchenQueueDto map(PreparationTask preparationTask) {
        return new KitchenQueueDto()
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
