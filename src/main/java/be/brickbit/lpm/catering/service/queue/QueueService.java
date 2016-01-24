package be.brickbit.lpm.catering.service.queue;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.repository.PreparationTaskRepository;
import be.brickbit.lpm.catering.service.queue.mapper.QueueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueService implements IQueueService {

    @Autowired
    private PreparationTaskRepository preparationTaskRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public <T> List<T> queueTasks(Long orderId, QueueMapper<T> dtoMapper){
        Order order = orderRepository.findOne(orderId);
        return order.getOrderLines()
                .stream()
                .filter(orderLine -> orderLine.getProduct().getPreparation() != null)
                .map(orderLine -> queueTask(orderLine, dtoMapper))
                .collect(Collectors.toList());
    }

    private <T> T queueTask(OrderLine orderLine, QueueMapper<T> dtoMapper) {
        PreparationTask task = new PreparationTask();

        task.setOrderLine(orderLine);
        task.setQueueTime(LocalDateTime.now());
        task.setStatus(OrderStatus.QUEUED);

        return dtoMapper.map(preparationTaskRepository.save(task));
    }
}
