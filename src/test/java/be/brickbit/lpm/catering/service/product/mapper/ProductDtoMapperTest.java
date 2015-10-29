package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.ReceiptDtoFixture;
import be.brickbit.lpm.catering.service.product.dto.ProductDto;
import be.brickbit.lpm.catering.service.product.dto.ReceiptDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDtoMapperTest {

    @Mock
    private ReceiptDtoMapper receiptDtoMapper;

    @InjectMocks
    private ProductDtoMapper mapper;

    @Test
    public void testMap() throws Exception {
        Product product = ProductFixture.getProduct();
        ProductDto productDto = mapper.map(product);
        ReceiptDto receiptLine1Dto = ReceiptDtoFixture.getReceiptLine1Dto();

        when(receiptDtoMapper.map(product.getReceipt().get(0))).thenReturn(receiptLine1Dto);

        assertThat(productDto.getClearanceType()).isEqualTo(product.getClearance());
        assertThat(productDto.getName()).isEqualTo(product.getName());
        assertThat(productDto.getProductType()).isEqualTo(product.getProductType());
        assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
        assertThat(productDto.getReceipt().size()).isEqualTo(1);
    }
}