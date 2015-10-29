package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
