package be.brickbit.lpm.catering.service.product;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.infrastructure.exception.ServiceException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.fixture.CreateProductCommandFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.catering.service.product.dto.ProductDto;
import be.brickbit.lpm.catering.service.product.mapper.CreateProductCommandToEntityMapper;
import be.brickbit.lpm.catering.service.product.mapper.ProductDtoMapper;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CreateProductCommandToEntityMapper productCommandToEntityMapper;
    @Mock
    private ProductDtoMapper dtoMapper;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ProductService productService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSaveProduct() throws Exception {
        CreateProductCommand createProductCommand = CreateProductCommandFixture.mutable();
        Product product = new Product();
        ProductDto productDto = new ProductDto();

        when(productCommandToEntityMapper.map(createProductCommand)).thenReturn(product);
        when(dtoMapper.map(product)).thenReturn(productDto);

        ProductDto result = productService.save(createProductCommand, dtoMapper);

        assertThat(result).isSameAs(productDto);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void findAllQueueNames() throws Exception {
        List<String> queueNames = new ArrayList<>();

        when(productRepository.findAllQueueNames()).thenReturn(queueNames);

        List<String> result = productService.findAllQueueNames();

        assertThat(result).isSameAs(queueNames);
    }

    @Test
    public void toggleAvailability() throws Exception {
        Product product = ProductFixture.getJupiler();
        Long productId = randomLong();
        Boolean productAvailable = true;
        product.setAvailable(productAvailable);

        when(productRepository.findOne(productId)).thenReturn(product);

        productService.toggleAvailability(productId);

        assertThat(product.getAvailable()).isEqualTo(!productAvailable);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void toggleAvailability__InvalidProduct() throws Exception {
        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Product not found");

        productService.toggleAvailability(randomLong());
    }

    @Test
    public void findAllByType() throws Exception {
        List<Product> products = Arrays.asList(ProductFixture.getPizza(), ProductFixture.getPizza());
        ProductType productType = ProductType.FOOD;

        when(productRepository.findByProductType(productType)).thenReturn(products);
        when(dtoMapper.map(any(Product.class))).thenReturn(new ProductDto());

        List<ProductDto> result = productService.findAllByType(productType, dtoMapper);

        assertThat(result).hasSize(products.size());
    }

    @Test
    public void findAllEnabledByType() throws Exception {
        List<Product> products = Arrays.asList(ProductFixture.getPizza(), ProductFixture.getPizza());
        ProductType productType = ProductType.FOOD;

        when(productRepository.findByProductTypeAndAvailableTrue(productType)).thenReturn(products);
        when(dtoMapper.map(any(Product.class))).thenReturn(new ProductDto());

        List<ProductDto> result = productService.findAllEnabledByType(productType, dtoMapper);

        assertThat(result).hasSize(products.size());
    }

    @Test
    public void deletesProduct__ProductIsUsed() throws Exception {
        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Can not delete, product has entered order lifecycle.");

        Long productId = randomLong();

        when(orderRepository.countByOrderLinesProductId(productId)).thenReturn(randomInt(1, 999));

        productService.delete(productId);
    }

    @Test
    public void deletesProduct() throws Exception {
        Long productId = randomLong();

        when(orderRepository.countByOrderLinesProductId(productId)).thenReturn(0);

        productService.delete(productId);
    }
}