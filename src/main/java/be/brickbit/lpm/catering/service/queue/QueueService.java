package be.brickbit.lpm.catering.service.queue;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.repository.PreparationTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class QueueService implements IQueueService {

    @Autowired
    private PreparationTaskRepository preparationTaskRepository;

    @Override
    @Transactional
    public void queueTasks(Order order){
        order.getOrderLines().stream().filter(orderLine -> orderLine.getProduct().getPreparation() != null).collect(Collectors.toList()).forEach(this::queueTask);
    }

    @Override
    @Transactional
    public void queueTask(OrderLine orderLine) {
        PreparationTask task = new PreparationTask();

        task.setOrderLine(orderLine);
        task.setQueueTime(LocalDateTime.now());
        task.setStatus(OrderStatus.QUEUED);

        preparationTaskRepository.save(task);
    }
}
