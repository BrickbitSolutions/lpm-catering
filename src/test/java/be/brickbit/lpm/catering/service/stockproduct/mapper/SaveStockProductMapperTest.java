package be.brickbit.lpm.catering.service.stockproduct.mapper;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockProductCommandFixture;
import be.brickbit.lpm.catering.service.stockproduct.command.SaveStockProductCommand;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SaveStockProductMapperTest {

    private SaveStockProductMapper mapper = new SaveStockProductMapper();

    @Test
    public void testMapNoStock() throws Exception {
        SaveStockProductCommand command = StockProductCommandFixture.getSaveCommand();
        command.setStockLevel(0);

        StockProduct stockProduct = mapper.map(command);

        assertThat(stockProduct.getName()).isEqualTo(command.getName());
        assertThat(stockProduct.getStockLevel()).isEqualTo(command.getStockLevel());
        assertThat(stockProduct.getProductType()).isEqualTo(command.getProductType());
        assertThat(stockProduct.getClearance()).isEqualTo(command.getClearance());
        assertThat(stockProduct.getMaxConsumptions()).isEqualTo(command.getMaxConsumptions());
        assertThat(stockProduct.getRemainingConsumptions()).isEqualTo(0);
    }

    @Test
    public void testMapBaseStock() throws Exception {
        SaveStockProductCommand command = StockProductCommandFixture.getSaveCommand();
        command.setStockLevel(10);

        StockProduct stockProduct = mapper.map(command);

        assertThat(stockProduct.getName()).isEqualTo(command.getName());
        assertThat(stockProduct.getStockLevel()).isEqualTo(command.getStockLevel());
        assertThat(stockProduct.getProductType()).isEqualTo(command.getProductType());
        assertThat(stockProduct.getClearance()).isEqualTo(command.getClearance());
        assertThat(stockProduct.getMaxConsumptions()).isEqualTo(command.getMaxConsumptions());
        assertThat(stockProduct.getRemainingConsumptions()).isEqualTo(command.getMaxConsumptions());
    }
}