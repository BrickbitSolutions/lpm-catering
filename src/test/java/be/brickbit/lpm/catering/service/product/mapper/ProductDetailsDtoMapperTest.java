package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.ReceiptDtoFixture;
import be.brickbit.lpm.catering.service.product.dto.ProductDetailsDto;
import be.brickbit.lpm.catering.service.product.dto.ReceiptDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsDtoMapperTest {
    @Mock
    private ReceiptDtoMapper receiptDtoMapper;

    @InjectMocks
    private ProductDetailsDtoMapper mapper;

    @Test
    public void testMap__NoQueue() throws Exception {
        Product product = ProductFixture.getJupiler();
        ReceiptDto receiptLine1Dto = ReceiptDtoFixture.getReceiptLine1Dto();

        when(receiptDtoMapper.map(product.getReceipt().get(0))).thenReturn(receiptLine1Dto);

        ProductDetailsDto detailsDto = mapper.map(product);

        assertThat(detailsDto.getQueueName()).isEmpty();
        assertThat(detailsDto.getTimerInMinutes()).isEqualTo(0);
        assertThat(detailsDto.getInstructions()).isEmpty();
        assertThat(detailsDto.getProductsToInclude().size()).isEqualTo(1);
        assertThat(detailsDto.getProductsToInclude().get(0)).isSameAs(receiptLine1Dto);
    }

    @Test
    public void testMap() throws Exception {
        Product product = ProductFixture.getPizza();
        ReceiptDto receiptLine1Dto = ReceiptDtoFixture.getReceiptLinePizza();

        when(receiptDtoMapper.map(product.getReceipt().get(0))).thenReturn(receiptLine1Dto);

        ProductDetailsDto detailsDto = mapper.map(product);

        assertThat(detailsDto.getQueueName()).isEqualTo(product.getPreparation().getQueueName());
        assertThat(detailsDto.getTimerInMinutes()).isEqualTo(product.getPreparation().getTimer() / 60);
        assertThat(detailsDto.getInstructions()).isEqualTo(product.getPreparation().getInstructions());
        assertThat(detailsDto.getProductsToInclude().size()).isEqualTo(1);
        assertThat(detailsDto.getProductsToInclude().get(0)).isSameAs(receiptLine1Dto);
    }
}