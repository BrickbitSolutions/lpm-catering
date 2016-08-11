package be.brickbit.lpm.catering.service.product;

import java.util.List;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.catering.service.product.mapper.ProductMapper;
import be.brickbit.lpm.infrastructure.Service;
import org.springframework.transaction.annotation.Transactional;

public interface IProductService extends Service<Product> {
	<T> T save(CreateProductCommand command, ProductMapper<T> dtoMapper);
	<T> List<T> findAllByType(ProductType productType, ProductMapper<T> dtoMapper);
    <T> List<T> findAllEnabledByType(ProductType productType, ProductMapper<T> dtoMapper);
	List<String> findAllQueueNames();
	void toggleAvailability(Long id);
    void delete(Long id);
}
