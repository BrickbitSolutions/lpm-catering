package be.brickbit.lpm.catering.service.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.catering.service.product.mapper.CreateProductCommandToEntityMapper;
import be.brickbit.lpm.catering.service.product.mapper.ProductMapper;
import be.brickbit.lpm.infrastructure.AbstractService;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@Service
public class ProductService extends AbstractService<Product> implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CreateProductCommandToEntityMapper productCommandToEntityMapper;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	@Transactional
	public <T> T save(CreateProductCommand command, ProductMapper<T> dtoMapper) {
		Product product = productCommandToEntityMapper.map(command);
		productRepository.save(product);
		return dtoMapper.map(product);
	}

	@Override
	public List<String> findAllQueueNames() {
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

	@Transactional
	@Override
	public void delete(Long id){
		if(orderRepository.countByOrderLinesProductId(id) > 0){
			throw new ServiceException("Can not delete, product has entered order lifecycle.");
		}

		productRepository.delete(id);
	}

	private ServiceException throwNotFoundException() {
		throw new ServiceException("Product not found");
	}

	@Override
	protected ProductRepository getRepository() {
		return productRepository;
	}
}
