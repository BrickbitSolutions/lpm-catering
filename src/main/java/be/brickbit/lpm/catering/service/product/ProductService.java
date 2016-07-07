package be.brickbit.lpm.catering.service.product;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.catering.service.product.mapper.CreateProductCommandToEntityMapper;
import be.brickbit.lpm.catering.service.product.mapper.ProductMapper;
import be.brickbit.lpm.infrastructure.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractService<Product> implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CreateProductCommandToEntityMapper productCommandToEntityMapper;

    @Override
    @Transactional
    public <T> T save(CreateProductCommand command, ProductMapper<T> dtoMapper) {
        Product product = productCommandToEntityMapper.map(command);
        productRepository.save(product);
        return dtoMapper.map(product);
    }

    @Override
    public List<String> findAllQueueNames(){
        return productRepository.findAllQueueNames();
    }

    @Override
    public void toggleAvailability(Long id) {
        Product product = Optional.ofNullable(productRepository.findOne(id)).orElseThrow(this::throwNotFoundException);
        product.setAvailable(!product.getAvailable());
        productRepository.save(product);
    }

    @Override
    public <T> List<T> findAllByType(ProductType productType, ProductMapper<T> dtoMapper) {
        return productRepository.findByProductType(productType).stream().map(dtoMapper::map).collect(Collectors.toList());
    }

    @Override
    public <T> List<T> findAllEnabledByType(ProductType productType, ProductMapper<T> dtoMapper) {
        return productRepository.findByProductTypeAndAvailableTrue(productType).stream().map(dtoMapper::map).collect(Collectors.toList());
    }

    private EntityNotFoundException throwNotFoundException() {
        throw new EntityNotFoundException("Product not found.");
    }

    @Override
    protected ProductRepository getRepository() {
        return productRepository;
    }
}
