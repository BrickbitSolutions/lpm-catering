package be.brickbit.lpm.catering.service.queue.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QueueDtoMapper implements QueueMapper<QueueDto> {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public QueueDto map(PreparationTask preparationTask) {
        return new QueueDto()
                .setTaskId(preparationTask.getId())
                .setDescription(getDescription(preparationTask))
                .setInstructions(preparationTask.getOrderLine().getProduct().getPreparation().getInstructions())
                .setDuration(preparationTask.getOrderLine().getProduct().getPreparation().getTimer())
                .setStartTime(preparationTask.getStartTime())
                .setQueueName(preparationTask.getOrderLine().getProduct().getPreparation().getQueueName())
                .setComment(findCommentFromOrder(preparationTask.getOrderLine().getId()));
    }

    private String findCommentFromOrder(Long orderLineId) {
        Optional<Order> order = Optional.ofNullable(orderRepository.findByOrderLinesId(orderLineId));

        if(order.isPresent()){
            return order.get().getComment();
        }else{
            return null;
        }
    }

    private String getDescription(PreparationTask preparationTask) {
        return preparationTask.getOrderLine().getQuantity() + "x "
                +  preparationTask.getOrderLine().getProduct().getName();
    }
}
