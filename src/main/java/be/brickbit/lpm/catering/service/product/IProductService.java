package be.brickbit.lpm.catering.service.product;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.service.product.command.ProductCommand;
import be.brickbit.lpm.catering.service.product.mapper.ProductMapper;
import be.brickbit.lpm.infrastructure.Service;

public interface IProductService extends Service<Product> {
    <T> T save(ProductCommand command, ProductMapper<T> dtoMapper);
    void delete(Long id);
}
