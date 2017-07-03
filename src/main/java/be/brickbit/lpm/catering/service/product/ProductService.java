package be.brickbit.lpm.catering.service.product;

import java.util.List;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.catering.service.product.command.EditProductCommand;
import be.brickbit.lpm.catering.service.product.command.EditProductPreparationCommand;
import be.brickbit.lpm.catering.service.product.mapper.ProductMapper;
import be.brickbit.lpm.infrastructure.Service;

public interface ProductService extends Service<Product> {
	<T> T save(CreateProductCommand command, ProductMapper<T> dtoMapper);
    <T> List<T> findAllByType(ProductType productType, Boolean enabledOnly, Boolean reservationOnly, ProductMapper<T> dtoMapper);
    <T> T updateProduct(Long productId, EditProductCommand command, ProductMapper<T> dtoMapper);
    <T> T updateProductPreparation(Long productId, EditProductPreparationCommand command, ProductMapper<T> dtoMapper);
    List<String> findAllQueueNames();

    void toggleAvailability(Long id);
    void delete(Long id);
}
