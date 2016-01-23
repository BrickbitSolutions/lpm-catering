package be.brickbit.lpm.catering.service.queue;

import be.brickbit.lpm.catering.service.queue.mapper.QueueMapper;

import java.util.List;

public interface IQueueService {
    <T> List<T> queueTasks(Long orderId, QueueMapper<T> dtoMapper);
}
