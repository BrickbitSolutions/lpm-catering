package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductPreparation;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.fixture.ProductCommandFixture;
import be.brickbit.lpm.catering.fixture.ProductReceiptLineFixture;
import be.brickbit.lpm.catering.service.product.command.ProductCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductCommandToEntityMapperTest {

    @Mock
    private ReceiptCommandToEntityMapper receiptCommandToEntityMapper;

    @InjectMocks
    private ProductCommandToEntityMapper productCommandToEntityMapper;

    @Test
    public void testMap() throws Exception {
        ProductCommand command = ProductCommandFixture.getProductCommand();
        ProductReceiptLine receiptLine1 = ProductReceiptLineFixture.getCola();
        ProductReceiptLine receiptLine2 = ProductReceiptLineFixture.getJupiler();

        when(receiptCommandToEntityMapper.map(command.getReceipt().get(0))).thenReturn(receiptLine1);
        when(receiptCommandToEntityMapper.map(command.getReceipt().get(1))).thenReturn(receiptLine2);

        Product product = productCommandToEntityMapper.map(command);

        assertThat(product.getName()).isEqualTo(command.getName());
        assertThat(product.getPrice()).isEqualTo(command.getPrice());
        assertThat(product.getProductType()).isEqualTo(command.getProductType());
        assertThat(product.getReceipt().size()).isEqualTo(2);
        assertThat(product.getReceipt().contains(receiptLine1)).isTrue();
        assertThat(product.getReceipt().contains(receiptLine2)).isTrue();
        assertThat(product.getClearance()).isEqualTo(ClearanceType.PLUS_16);
        assertThat(product.getPreparation()).isNull();
        assertThat(product.getAvailable()).isFalse();
    }

    @Test
    public void testMapFood() throws Exception {
        ProductCommand command = ProductCommandFixture.getProductCommandFood();
        ProductReceiptLine receiptLine1 = ProductReceiptLineFixture.getPizza();

        when(receiptCommandToEntityMapper.map(command.getReceipt().get(0))).thenReturn(receiptLine1);

        Product product = productCommandToEntityMapper.map(command);

        assertThat(product.getName()).isEqualTo(command.getName());
        assertThat(product.getPrice()).isEqualTo(command.getPrice());
        assertThat(product.getProductType()).isEqualTo(command.getProductType());
        assertThat(product.getReceipt().size()).isEqualTo(1);
        assertThat(product.getReceipt().contains(receiptLine1)).isTrue();
        assertThat(product.getClearance()).isEqualTo(ClearanceType.ANY);
        assertThat(product.getPreparation()).isNotNull();

        ProductPreparation preparation = product.getPreparation();

        assertThat(preparation.getInstructions()).isEqualTo(command.getInstructions());
        assertThat(preparation.getQueueName()).isEqualTo(command.getQueueName());
        assertThat(preparation.getTimer()).isEqualTo(command.getTimerInMinutes());

    }
}