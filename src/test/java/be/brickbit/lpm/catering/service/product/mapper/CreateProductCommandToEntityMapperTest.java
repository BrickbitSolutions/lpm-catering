package be.brickbit.lpm.catering.service.product.mapper;

import com.google.common.collect.Lists;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductPreparation;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.ProductCommandFixture;
import be.brickbit.lpm.catering.fixture.ProductReceiptLineFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateProductCommandToEntityMapperTest {

    @Mock
    private ReceiptCommandToEntityMapper receiptCommandToEntityMapper;

    @Mock
    private StockProductRepository stockProductRepository;

    @InjectMocks
    private CreateProductCommandToEntityMapper productCommandToEntityMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testMap__NoQueue() throws Exception {
        CreateProductCommand command = ProductCommandFixture.getProductCommand();
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
        assertThat(product.getAvgConsumption()).isEqualTo(0);
        assertThat(product.getPreparation()).isNull();
        assertThat(product.getAvailable()).isFalse();
        assertThat(product.getReservationOnly()).isEqualTo(command.getReservationOnly());
    }

    @Test
    public void testMap__QueuePresent() throws Exception {
        CreateProductCommand command = ProductCommandFixture.getProductCommandFood();
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
        assertThat(product.getReservationOnly()).isEqualTo(command.getReservationOnly());

        ProductPreparation preparation = product.getPreparation();

        assertThat(preparation.getInstructions()).isEqualTo(command.getInstructions());
        assertThat(preparation.getQueueName()).isEqualTo(command.getQueueName());
        assertThat(preparation.getTimer()).isEqualTo(command.getTimerInMinutes() * 60);

    }

    @Test
    public void mapsSupplements() throws Exception {
        CreateProductCommand command = ProductCommandFixture.getProductCommandFood();
        Long supplProdId = randomLong();
        command.setSupplements(Lists.newArrayList(supplProdId));

        ProductReceiptLine receiptLine1 = ProductReceiptLineFixture.getPizza();
        StockProduct stockProduct = StockProductFixture.getCheese();

        when(receiptCommandToEntityMapper.map(command.getReceipt().get(0))).thenReturn(receiptLine1);
        when(stockProductRepository.findOne(supplProdId)).thenReturn(stockProduct);

        Product product = productCommandToEntityMapper.map(command);

        assertThat(product.getSupplements()).containsExactly(stockProduct);
    }

    @Test
    public void throwsStockProductNotFoundWithInvalidSupplement() throws Exception {
        CreateProductCommand command = ProductCommandFixture.getProductCommandFood();
        Long supplProdId = randomLong();
        command.setSupplements(Lists.newArrayList(supplProdId));

        ProductReceiptLine receiptLine1 = ProductReceiptLineFixture.getPizza();

        when(receiptCommandToEntityMapper.map(command.getReceipt().get(0))).thenReturn(receiptLine1);

        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage(String.format("Stock product #%d was not found", supplProdId));

        productCommandToEntityMapper.map(command);
    }
}