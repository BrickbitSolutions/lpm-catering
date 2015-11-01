package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.domain.PreparationTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreparationTaskRepository extends JpaRepository<PreparationTask, Long> {
}
