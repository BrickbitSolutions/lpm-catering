package be.brickbit.lpm.catering.service.product;

import java.util.List;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.catering.service.product.mapper.ProductMapper;
import be.brickbit.lpm.infrastructure.Service;

public interface IProductService extends Service<Product> {
	<T> List<T> findAllByType(ProductType productType, ProductMapper<T> dtoMapper);
    <T> List<T> findAllEnabledByType(ProductType productType, ProductMapper<T> dtoMapper);

	<T> T save(CreateProductCommand command, ProductMapper<T> dtoMapper);

	List<String> findAllQueueNames();

	void toggleAvailability(Long id);

}
