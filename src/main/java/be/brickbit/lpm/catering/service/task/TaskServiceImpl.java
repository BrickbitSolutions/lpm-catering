package be.brickbit.lpm.catering.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.repository.PreparationTaskRepository;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private PreparationTaskRepository preparationTaskRepository;

    @Override
    @Transactional
    public void updateTaskWithStatus(Long taskId, OrderStatus status, UserPrincipalDto currentUser) {
        Optional<PreparationTask> task = Optional.ofNullable(preparationTaskRepository.findOne(taskId));

        if (task.isPresent()) {
            task.get().setStatus(status);
            task.get().getOrderLine().setStatus(status);

            if(status == OrderStatus.IN_PROGRESS){
                task.get().setStartTime(LocalDateTime.now());
                task.get().setUserId(currentUser.getId());
            }

            preparationTaskRepository.save(task.get());
        } else {
            throw new ServiceException(String.format("Invalid Task ID (#%s)", taskId));
        }
    }
}
