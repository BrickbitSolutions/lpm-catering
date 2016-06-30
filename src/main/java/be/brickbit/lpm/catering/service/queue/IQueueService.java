package be.brickbit.lpm.catering.service.queue;

import java.util.List;

import be.brickbit.lpm.catering.service.queue.mapper.QueueMapper;

public interface IQueueService {
	<T> List<T> findAllTasks(String QueueName, QueueMapper<T> dtoMapper);

	<T> List<T> queueAllTasks(Long orderId, QueueMapper<T> dtoMapper);

	<T> List<T> queueAllTasksDirectOrder(Long orderId, QueueMapper<T> dtoMapper);
}
