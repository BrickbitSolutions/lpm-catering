package be.brickbit.lpm.catering.controller.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.controller.dto.QueueDto;
import be.brickbit.lpm.catering.service.queue.mapper.QueueMapper;
import be.brickbit.lpm.catering.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class QueueDtoMapper implements QueueMapper<QueueDto> {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public QueueDto map(PreparationTask preparationTask) {
        return new QueueDto(
                preparationTask.getId(),
                getDescription(preparationTask),
                preparationTask.getStartTime(),
                preparationTask.getOrderLine().getProduct().getPreparation().getTimer(),
                getTimeRemaining(preparationTask),
                preparationTask.getOrderLine().getProduct().getPreparation().getInstructions(),
                preparationTask.getOrderLine().getProduct().getPreparation().getQueueName(),
                findCommentFromOrder(preparationTask.getOrderLine().getId()),
                preparationTask.getOrderLine().getProduct().getSupplements().stream().map(StockProduct::getName).collect(Collectors.toList())
        );
    }

    private Long getTimeRemaining(PreparationTask preparationTask) {
        if(preparationTask.getStartTime() != null){
            Long remainingTime = preparationTask.getOrderLine().getProduct().getPreparation().getTimer() - DateUtils.calculateDifference(preparationTask.getStartTime(), LocalDateTime.now());
            if(remainingTime >= 0){
                return remainingTime;
            }else{
                return 0L;
            }
        }else{
            return Long.valueOf(preparationTask.getOrderLine().getProduct().getPreparation().getTimer());
        }
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
