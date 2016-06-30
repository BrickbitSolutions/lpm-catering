package be.brickbit.lpm.catering.service.product;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.service.product.command.ProductCommand;
import be.brickbit.lpm.catering.service.product.mapper.ProductCommandToEntityMapper;
import be.brickbit.lpm.catering.service.product.mapper.ProductMapper;
import be.brickbit.lpm.infrastructure.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractService<Product> implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCommandToEntityMapper productCommandToEntityMapper;

    @Override
    @Transactional
    public <T> T save(ProductCommand command, ProductMapper<T> dtoMapper) {
        Product product = productCommandToEntityMapper.map(command);
        productRepository.save(product);
        return dtoMapper.map(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.delete(id);
    }

    @Override
    public List<String> findAllQueueNames(){
        return productRepository.findAllQueueNames();
    }

    @Override
    public <T> List<T> findAllByType(ProductType productType, ProductMapper<T> dtoMapper) {
        return productRepository.findByProductType(productType).stream().map(dtoMapper::map).collect(Collectors.toList());
    }

    @Override
    protected ProductRepository getRepository() {
        return productRepository;
    }
}
