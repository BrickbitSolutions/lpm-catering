package be.brickbit.lpm.catering.service.task;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.repository.PreparationTaskRepository;
import be.brickbit.lpm.catering.service.order.IOrderService;
import be.brickbit.lpm.catering.service.order.OrderService;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.service.order.mapper.OrderDtoMapper;
import be.brickbit.lpm.catering.service.queue.dto.QueueDto;
import be.brickbit.lpm.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private PreparationTaskRepository preparationTaskRepository;

    @Autowired
    private OrderDtoMapper orderDtoMapper;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void updateTaskWithStatus(Long taskId, OrderStatus status, User currentUser) {
        Optional<PreparationTask> task = Optional.ofNullable(preparationTaskRepository.findOne(taskId));

        if(task.isPresent()){
            task.get().setStatus(status);
            task.get().getOrderLine().setStatus(status);

            switch (status){
                case IN_PROGRESS:
                    task.get().setStartTime(LocalDateTime.now());
                    task.get().setUserId(currentUser.getId());
                    break;
                case READY:
                    pushToQueue(orderService.findOrderByOrderLineId(task.get().getOrderLine().getId(), orderDtoMapper));
                    break;
            }

            preparationTaskRepository.save(task.get());
        }else{
            throw new EntityNotFoundException(String.format("Task %s does not exist in the database", taskId));
        }
    }

    private void pushToQueue(OrderDto message){
        messagingTemplate.convertAndSend("/topic/zanzibar.queue", message);
    }
}
