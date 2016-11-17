package be.brickbit.lpm.catering.controller;

import com.google.common.collect.Lists;

import com.mysema.query.jpa.impl.JPAQuery;

import org.junit.Test;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.domain.QPreparationTask;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.PreparationTaskFixture;

import static be.brickbit.lpm.catering.domain.OrderStatus.IN_PROGRESS;
import static be.brickbit.lpm.catering.domain.OrderStatus.READY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerIT extends AbstractControllerIT {
    @Test
    public void startsTask() throws Exception {
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

        performPut(String.format("/task/%s/start", preparationTask.getId()), null)
                .andExpect(status().isNoContent());

        PreparationTask result = new JPAQuery(getEntityManager())
                .from(QPreparationTask.preparationTask)
                .where(QPreparationTask.preparationTask.id.eq(preparationTask.getId()))
                .uniqueResult(QPreparationTask.preparationTask);

        assertThat(result.getStartTime()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userDetails().getId());
        assertThat(result.getStatus()).isEqualTo(IN_PROGRESS);
    }

    @Test
    public void completesTask() throws Exception {
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

        stubCore("/user/1", 200, userDetails());

        performPut(String.format("/task/%s/complete", preparationTask.getId()), null)
                .andExpect(status().isNoContent());

        PreparationTask result = new JPAQuery(getEntityManager())
                .from(QPreparationTask.preparationTask)
                .where(QPreparationTask.preparationTask.id.eq(preparationTask.getId()))
                .uniqueResult(QPreparationTask.preparationTask);

        assertThat(result.getStatus()).isEqualTo(READY);
    }
}