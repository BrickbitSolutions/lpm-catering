package be.brickbit.lpm.catering.service.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.service.product.command.EditProductCommand;
import be.brickbit.lpm.catering.service.product.command.EditProductPreparationCommand;
import be.brickbit.lpm.catering.service.product.mapper.ProductMerger;
import be.brickbit.lpm.catering.service.product.mapper.ProductPreparationMerger;
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

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private ProductMerger productMerger;

	@Autowired
	private ProductPreparationMerger productPreparationMerger;

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

	@Override
	@Transactional
	public <T> T updateProduct(Long productId, EditProductCommand command, ProductMapper<T> dtoMapper) {
		Product product = Optional.ofNullable(productRepository.findOne(productId)).orElseThrow(this::throwNotFoundException);

		productMerger.merge(command, product);

		return dtoMapper.map(product);
	}

	@Override
	@Transactional
	public <T> T updateProductPreparation(Long productId, EditProductPreparationCommand command, ProductMapper<T> dtoMapper){
		Product product = Optional.ofNullable(productRepository.findOne(productId)).orElseThrow(this::throwNotFoundException);

		productPreparationMerger.merge(command, product);

		return dtoMapper.map(product);
	}

	@Transactional
	@Override
	public void delete(Long id){
		Product product = Optional.ofNullable(productRepository.findOne(id)).orElseThrow(this::throwNotFoundException);

		if(product.getAvailable()){
			throw new ServiceException("Cannot delete, product still enabled.");
		}

		if(orderRepository.countByOrderLinesProductId(id) > 0){
			throw new ServiceException("Cannot delete, product has entered order lifecycle.");
		}

		productImageService.deleteProductImage(Long.toString(id));
		productRepository.delete(product);
	}

	private ServiceException throwNotFoundException() {
		throw new ServiceException("Product not found");
	}

	@Override
	protected ProductRepository getRepository() {
		return productRepository;
	}
}
