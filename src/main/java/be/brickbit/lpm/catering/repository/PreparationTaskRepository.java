package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.domain.PreparationTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PreparationTaskRepository extends JpaRepository<PreparationTask, Long>{

    @Query("SELECT t FROM PreparationTask t WHERE t.orderLine.product.preparation.queueName = :queueName AND (t.status = 'IN_PROGRESS' OR t.status = 'QUEUED')")
    List<PreparationTask> findAllByQueueName(@Param("queueName") String queueName);
}
