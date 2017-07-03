package be.brickbit.lpm.catering.service.queue;

import java.util.List;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.service.queue.mapper.QueueMapper;

public interface QueueService {
	<T> List<T> queueOrder(Order order, QueueMapper<T> dtoMapper);

	<T> List<T> findAllTasks(String QueueName, QueueMapper<T> dtoMapper);
}
