package be.brickbit.lpm.catering.service.queue;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.service.queue.mapper.QueueMapper;

public interface QueueService {
	@Transactional
	<T> List<T> queueOrder(Order order, QueueMapper<T> dtoMapper);
	<T> List<T> findAllTasks(String QueueName, QueueMapper<T> dtoMapper);
}
