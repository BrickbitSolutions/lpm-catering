package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.PreparationTask;

import java.time.LocalDateTime;

public class PreparationTaskFixture {
    public static PreparationTask getNewPreparationTask(){
        PreparationTask task = new PreparationTask();

        task.setId(1L);
        task.setOrderLine(OrderLineFixture.getPizzaOrderLine());
        task.setQueueTime(LocalDateTime.now());
        task.setStatus(OrderStatus.QUEUED);

        return task;
    }
}
