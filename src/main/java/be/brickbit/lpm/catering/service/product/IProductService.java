package be.brickbit.lpm.catering.service.product;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.product.command.ProductCommand;
import be.brickbit.lpm.catering.service.product.dto.ProductDetailsDto;
import be.brickbit.lpm.catering.service.product.dto.ProductDto;
import be.brickbit.lpm.catering.service.product.mapper.ProductDetailsDtoMapper;
import be.brickbit.lpm.catering.service.product.mapper.ProductDtoMapper;
import be.brickbit.lpm.catering.service.product.mapper.ProductMapper;
import be.brickbit.lpm.infrastructure.Service;

import java.util.List;

public interface IProductService extends Service<Product> {
    <T> List<T> findAllByType(ProductType productType, ProductMapper<T> dtoMapper);
    <T> T save(ProductCommand command, ProductMapper<T> dtoMapper);
    void delete(Long id);
    List<String> findAllQueueNames();
}
