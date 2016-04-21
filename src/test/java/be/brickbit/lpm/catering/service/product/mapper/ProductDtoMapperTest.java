package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.service.product.dto.ProductDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProductDtoMapperTest {

    private ProductDtoMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ProductDtoMapper();
    }

    @Test
    public void testMap() throws Exception {
        Product product = ProductFixture.getJupiler();
        ProductDto productDto = mapper.map(product);

        assertThat(productDto.getClearanceType()).isEqualTo(product.getClearance());
        assertThat(productDto.getName()).isEqualTo(product.getName());
        assertThat(productDto.getProductType()).isEqualTo(product.getProductType());
        assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
    }
}