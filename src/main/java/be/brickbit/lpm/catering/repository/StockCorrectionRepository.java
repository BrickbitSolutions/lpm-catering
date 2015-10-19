package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.domain.StockCorrection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockCorrectionRepository extends JpaRepository<StockCorrection, Long> {
}
