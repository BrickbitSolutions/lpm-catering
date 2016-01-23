package be.brickbit.lpm.catering.service.queue;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;

public interface IQueueService {
    void queueTask(OrderLine orderLine);
    void queueTasks(Order order);
}
