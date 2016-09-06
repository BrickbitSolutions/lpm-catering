package be.brickbit.lpm.catering.service.task;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;

public interface TaskService {
    void updateTaskWithStatus(Long taskId, OrderStatus status, UserPrincipalDto currentUser);
}
