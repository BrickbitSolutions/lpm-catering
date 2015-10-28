package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.domain.StockFlow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockFlowRepository extends JpaRepository<StockFlow, Long> {
}
