package be.brickbit.lpm.catering.service.stockflow.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockFlowFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;

public class StockFlowUtilTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testProcessStockFlowCorrection() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		Integer result = StockFlowUtil.processStockFlow(detail, StockFlowType.CORRECTION);
		assertThat(result).isEqualTo(10);
	}

	@Test
	public void testProcessStockFlowLoss() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		Integer result = StockFlowUtil.processStockFlow(detail, StockFlowType.LOSS);
		assertThat(result).isEqualTo(10);
	}

	@Test
	public void testProcessStockFlowReturned() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		Integer result = StockFlowUtil.processStockFlow(detail, StockFlowType.RETURNED);
		assertThat(result).isEqualTo(10);
	}

	@Test
	public void testProcessStockFlowSold() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		Integer result = StockFlowUtil.processStockFlow(detail, StockFlowType.SOLD);
		assertThat(result).isEqualTo(10);
	}

	@Test
	public void testProcessStockFlowPurchased() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		Integer result = StockFlowUtil.processStockFlow(detail, StockFlowType.PURCHASED);
		assertThat(result).isEqualTo(30);
	}

	@Test
	public void testCalculateCurrentStockLevel__OneConsumption() throws Exception {
		StockProduct product = StockProductFixture.getStockProductCola();
		product.setRemainingConsumptions(1);
		product.setMaxConsumptions(1);
		product.setStockLevel(20);

		Integer result = StockFlowUtil.calculateCurrentStockLevel(product);

		assertThat(result).isEqualTo(product.getStockLevel());
	}

	@Test
	public void testCalculateCurrentStockLevel__MoreConsumptionsPerProduct() throws Exception {
		StockProduct product = StockProductFixture.getStockProductCola();
		product.setRemainingConsumptions(20);
		product.setMaxConsumptions(50);
		product.setStockLevel(20);

		Integer result = StockFlowUtil.calculateCurrentStockLevel(product);

		assertThat(result).isEqualTo(1020);
	}

	@Test
	public void testCalculateNewStockLevel__NoEnoughStock() throws Exception {
		StockProduct product = StockProductFixture.getStockProductCola();
		product.setRemainingConsumptions(5);
		product.setMaxConsumptions(5);
		product.setStockLevel(1);

		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(String.format("Not enough '%s' in stock to process order!", product.getName()));

		StockFlowUtil.calculateNewStockLevel(product, 20);
	}

	@Test
    public void testCalculateNewStockLevel__OneMaxConsumption() throws Exception {
        StockProduct product = StockProductFixture.getStockProductCola();
        product.setRemainingConsumptions(1);
        product.setMaxConsumptions(1);
        product.setStockLevel(5);

        StockFlowUtil.calculateNewStockLevel(product, 1);

        assertThat(product.getStockLevel()).isEqualTo(4);
    }

    @Test
    public void testCalculateNewStockLevel__MultipleConsumptionsPerProduct__EnoughRemainingConsumptions() throws Exception {
        StockProduct product = StockProductFixture.getStockProductCola();
        product.setRemainingConsumptions(5);
        product.setMaxConsumptions(5);
        product.setStockLevel(5);

        StockFlowUtil.calculateNewStockLevel(product, 1);

        assertThat(product.getRemainingConsumptions()).isEqualTo(4);
        assertThat(product.getMaxConsumptions()).isEqualTo(5);
        assertThat(product.getStockLevel()).isEqualTo(5);
    }

    @Test
    public void testCalculateNewStockLevel__MulitpleConsumptionsPerProduct__NotEnoughRemainingConsumptions() throws Exception {
        StockProduct product = StockProductFixture.getStockProductCola();
        product.setRemainingConsumptions(1);
        product.setMaxConsumptions(5);
        product.setStockLevel(5);

        StockFlowUtil.calculateNewStockLevel(product, 2);

        assertThat(product.getRemainingConsumptions()).isEqualTo(4);
        assertThat(product.getMaxConsumptions()).isEqualTo(5);
        assertThat(product.getStockLevel()).isEqualTo(4);
    }
}