package be.brickbit.lpm.catering.controller;

import com.google.common.collect.Lists;

import org.junit.Test;

import java.time.format.DateTimeFormatter;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.PreparationTaskFixture;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QueueControllerIT extends AbstractControllerIT {
    @Test
    public void findsAllTasks() throws Exception {
        PreparationTask preparationTask = PreparationTaskFixture.mutable();
        Order order = OrderFixture.mutable();
        order.setOrderLines(Lists.newArrayList(preparationTask.getOrderLine()));

        insert(
                preparationTask.getOrderLine().getProduct().getReceipt().get(0).getStockProduct(),
                preparationTask.getOrderLine().getProduct(),
                order,
                preparationTask.getOrderLine(),
                preparationTask
        );

        performGet(String.format("/queue/%s", preparationTask.getOrderLine().getProduct().getPreparation().getQueueName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].taskId", is(preparationTask.getId().intValue())))
                .andExpect(jsonPath("$[0].description", notNullValue()))
                .andExpect(jsonPath("$[0].startTime", is(preparationTask.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
                .andExpect(jsonPath("$[0].duration", is(preparationTask.getOrderLine().getProduct().getPreparation().getTimer())))
                .andExpect(jsonPath("$[0].timeRemaining", notNullValue()))
                .andExpect(jsonPath("$[0].instructions", is(preparationTask.getOrderLine().getProduct().getPreparation().getInstructions())))
                .andExpect(jsonPath("$[0].queueName", is(preparationTask.getOrderLine().getProduct().getPreparation().getQueueName())))
                .andExpect(jsonPath("$[0].comment", is(order.getComment())));
    }

    @Test
    public void findsAllQueueNames() throws Exception {
        PreparationTask preparationTask = PreparationTaskFixture.mutable();
        Order order = OrderFixture.mutable();
        order.setOrderLines(Lists.newArrayList(preparationTask.getOrderLine()));

        insert(
                preparationTask.getOrderLine().getProduct().getReceipt().get(0).getStockProduct(),
                preparationTask.getOrderLine().getProduct(),
                order,
                preparationTask.getOrderLine(),
                preparationTask
        );

        performGet("/queue")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsInAnyOrder(
                        preparationTask.getOrderLine().getProduct().getPreparation().getQueueName()
                )));
    }
}