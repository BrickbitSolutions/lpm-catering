package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.ReceiptCommandFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.product.command.ReceiptLineCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptCommandToEntityMapperTest {
    @Mock
    private StockProductRepository stockProductRepository;

    @InjectMocks
    private ReceiptCommandToEntityMapper mapper;

    @Test
    public void testMap() throws Exception {
        ReceiptLineCommand command = ReceiptCommandFixture.getReceiptLineCommand();
        StockProduct stockProduct = StockProductFixture.getStockProduct();

        when(stockProductRepository.findOne(command.getStockProductId())).thenReturn(stockProduct);

        ProductReceiptLine receiptLine = mapper.map(command);

        assertThat(receiptLine.getQuantity()).isEqualTo(command.getQuantity());
        assertThat(receiptLine.getStockProduct()).isSameAs(stockProduct);
    }
}