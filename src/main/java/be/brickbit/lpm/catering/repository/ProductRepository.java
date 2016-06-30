package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByProductType(ProductType someType);

    @Query("SELECT DISTINCT p.preparation.queueName FROM Product p")
    List<String> findAllQueueNames();
}
