package be.brickbit.lpm.catering.service.stockproduct.mapper;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import org.junit.Before;
import org.junit.Test;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.service.stockproduct.command.EditStockProductCommand;

import static org.assertj.core.api.Assertions.assertThat;

public class StockProductMergerTest {
	private StockProductMerger merger;

	@Before
	public void setUp() throws Exception {
		merger = new StockProductMerger();
	}

	@Test
	public void mergesName() throws Exception {
		StockProduct stockProduct = StockProductFixture.getStockProductCola();
		EditStockProductCommand command = new EditStockProductCommand();
		command.setName("New Name");
		command.setProductType(stockProduct.getProductType());
		command.setClearance(stockProduct.getClearance());

		merger.merge(command, stockProduct);

		assertThat(stockProduct.getName()).isEqualTo(command.getName());
	}

    @Test
    public void mergesProductType() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProductCola();
        EditStockProductCommand command = new EditStockProductCommand();
        command.setName(stockProduct.getName());
        command.setProductType(ProductType.FOOD);
        command.setClearance(stockProduct.getClearance());

        merger.merge(command, stockProduct);

        assertThat(stockProduct.getProductType()).isEqualTo(command.getProductType());
    }

    @Test
    public void mergesClearance() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProductCola();
        EditStockProductCommand command = new EditStockProductCommand();
        command.setName(stockProduct.getName());
        command.setProductType(stockProduct.getProductType());
        command.setClearance(ClearanceType.PLUS_21);

        merger.merge(command, stockProduct);

        assertThat(stockProduct.getClearance()).isEqualTo(command.getClearance());
    }
}