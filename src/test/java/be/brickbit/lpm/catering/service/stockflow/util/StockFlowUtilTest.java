package be.brickbit.lpm.catering.service.stockflow.util;

import static org.assertj.core.api.Assertions.assertThat;

import be.brickbit.lpm.infrastructure.exception.ServiceException;
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
		StockFlowUtil.processStockFlow(detail, StockFlowType.CORRECTION);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(10);
	}

	@Test
	public void testConsumptionProcessStockFlowCorrection__EnoughConsumptionsLeft() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(6);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.CORRECTION);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(4);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(1);
	}

	@Test
	public void testConsumptionProcessStockFlowCorrection__NotEnoughConsumptions() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(3);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.CORRECTION);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(3);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(4);
	}

	@Test
	public void testProcessStockFlow__QuantityTooBig() throws Exception {
		expectedException.expect(ServiceException.class);
		expectedException.expectMessage("Stock or Consumption level cannot fall below zero");

		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setStockLevel(1);

		StockFlowUtil.processStockFlow(detail, StockFlowType.LOSS);
	}

	@Test
	public void testProcessStockFlowLoss() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		StockFlowUtil.processStockFlow(detail, StockFlowType.LOSS);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(10);
	}

	@Test
	public void testConsumptionProcessStockFlowLoss__EnoughConsumptionsLeft() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(6);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.LOSS);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(4);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(1);
	}

	@Test
	public void testConsumptionProcessStockFlowLoss__NotEnoughConsumptions() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(3);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.LOSS);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(3);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(4);
	}

	@Test
	public void testProcessStockFlowReturned() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		StockFlowUtil.processStockFlow(detail, StockFlowType.RETURNED);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(10);
	}

	@Test
	public void testConsumptionProcessStockFlowReturned__EnoughConsumptionsLeft() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(6);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.RETURNED);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(4);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(1);
	}

	@Test
	public void testConsumptionProcessStockFlowReturned__NotEnoughConsumptions() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(3);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.RETURNED);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(3);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(4);
	}

	@Test
	public void testProcessStockFlowSold() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		StockFlowUtil.processStockFlow(detail, StockFlowType.SOLD);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(10);
	}

	@Test
	public void testConsumptionProcessStockFlowSold__LowerThanMax() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(4);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(5);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.SOLD);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(4);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(1);
	}

	@Test
	public void testConsumptionProcessStockFlowSold__HigherThanMax() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(3);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.SOLD);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(3);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(4);
	}

	@Test
	public void testProcessStockFlowPurchased() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		StockFlowUtil.processStockFlow(detail, StockFlowType.PURCHASED);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(30);
	}

	@Test
	public void testConsumptionProcessStockFlowPurchased__LowerThanMax() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(1);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.PURCHASED);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(4);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(6);
	}

	@Test
	public void testConsumptionProcessStockFlowPurchased__HigherThanMax() throws Exception {
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(5);
		detail.getStockProduct().setMaxConsumptions(6);
		detail.getStockProduct().setRemainingConsumptions(3);
		detail.getStockProduct().setStockLevel(4);
		StockFlowUtil.processConsumptionStockFlow(detail, StockFlowType.PURCHASED);
		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(5);
		assertThat(detail.getStockProduct().getRemainingConsumptions()).isEqualTo(2);
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
	public void testCalculateNewStockLevel__NotEnoughStock() throws Exception {
		StockProduct product = StockProductFixture.getStockProductCola();
		product.setRemainingConsumptions(5);
		product.setMaxConsumptions(5);
		product.setStockLevel(1);

		expectedException.expect(ServiceException.class);
		expectedException.expectMessage(String.format("Not enough '%s' in stock to process order!", product.getName()));

		StockFlowUtil.calculateNewStockLevel(product, -20);
	}

	@Test
    public void testCalculateNewStockLevel__OneMaxConsumption() throws Exception {
        StockProduct product = StockProductFixture.getStockProductCola();
        product.setRemainingConsumptions(1);
        product.setMaxConsumptions(1);
        product.setStockLevel(5);

        StockFlowUtil.calculateNewStockLevel(product, -1);

        assertThat(product.getStockLevel()).isEqualTo(4);
    }

    @Test
    public void testCalculateNewStockLevel__MultipleConsumptionsPerProduct__EnoughRemainingConsumptions() throws Exception {
        StockProduct product = StockProductFixture.getStockProductCola();
        product.setRemainingConsumptions(5);
        product.setMaxConsumptions(5);
        product.setStockLevel(5);

        StockFlowUtil.calculateNewStockLevel(product, -1);

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

        StockFlowUtil.calculateNewStockLevel(product, -2);

        assertThat(product.getRemainingConsumptions()).isEqualTo(4);
        assertThat(product.getMaxConsumptions()).isEqualTo(5);
        assertThat(product.getStockLevel()).isEqualTo(4);
    }
}