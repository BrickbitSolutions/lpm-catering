package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.domain.StockProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockProductRepository extends JpaRepository<StockProduct, Long> {
    List<StockProduct> findByProductTypeAndClearance(ProductType someType, ClearanceType someClearance);
    List<StockProduct> findByProductType(ProductType someType);
}
