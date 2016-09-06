package be.brickbit.lpm.catering.service.stockproduct.mapper;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockProductCommandFixture;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StockProductCommandToEntityMapperTest {

    private StockProductCommandToEntityMapper mapper = new StockProductCommandToEntityMapper();

    @Test
    public void testMapNoStock() throws Exception {
        StockProductCommand command = StockProductCommandFixture.mutable();
        command.setStockLevel(0);

        StockProduct stockProduct = mapper.map(command);

        assertThat(stockProduct.getName()).isEqualTo(command.getName());
        assertThat(stockProduct.getStockLevel()).isEqualTo(command.getStockLevel());
        assertThat(stockProduct.getProductType()).isEqualTo(command.getProductType());
        assertThat(stockProduct.getClearance()).isEqualTo(command.getClearance());
        assertThat(stockProduct.getMaxConsumptions()).isEqualTo(command.getMaxConsumptions());
        assertThat(stockProduct.getAvgConsumption()).isEqualTo(0);
        assertThat(stockProduct.getRemainingConsumptions()).isEqualTo(0);
    }

    @Test
    public void testMapMultipleConsumptionsPerProduct() throws Exception {
        StockProductCommand command = StockProductCommandFixture.mutable();
        command.setMaxConsumptions(2);
        command.setStockLevel(20);

        StockProduct stockProduct = mapper.map(command);

        assertThat(stockProduct.getStockLevel()).isEqualTo(command.getStockLevel() - 1);
        assertThat(stockProduct.getRemainingConsumptions()).isEqualTo(stockProduct.getMaxConsumptions());
        assertThat(stockProduct.getMaxConsumptions()).isEqualTo(command.getMaxConsumptions());
    }

    @Test
    public void testMapBaseStock() throws Exception {
        StockProductCommand command = StockProductCommandFixture.mutable();
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