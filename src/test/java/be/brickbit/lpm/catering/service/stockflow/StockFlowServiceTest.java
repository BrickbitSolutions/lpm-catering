package be.brickbit.lpm.catering.service.stockflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.*;
import be.brickbit.lpm.catering.service.stockflow.command.StockCorrectionLevel;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.repository.StockFlowRepository;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowDtoMapper;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowCommandToEntityMapper;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class StockFlowServiceTest {

	@Mock
	private StockFlowRepository stockFlowRepository;

	@Mock
	private StockFlowCommandToEntityMapper stockFlowCommandToEntityMapper;

	@Mock
	private StockFlowDtoMapper dtoMapper;

	@InjectMocks
	private StockFlowService stockFlowService;

	@Test
	public void testSave() throws Exception {
		StockFlowCommand command = StockFlowCommandFixture.mutable();
		StockFlow entity = StockFlowFixture.getStockFlow();
		StockFlowDto dto = StockFlowDtoFixture.getStockFlowDto();

		when(stockFlowCommandToEntityMapper.map(command)).thenReturn(entity);
		when(dtoMapper.map(entity)).thenReturn(dto);

		StockFlowDto result = stockFlowService.save(command, UserFixture.getCateringAdmin(), dtoMapper);

		assertThat(result).isSameAs(dto);

		verify(stockFlowRepository).save(entity);
	}

	@Test
	public void testSave__UpdateCorrectStock__StockLevel() throws Exception {
		StockFlowCommand command = StockFlowCommandFixture.mutable();
		command.setLevel(StockCorrectionLevel.STOCK);
		StockFlow entity = StockFlowFixture.getStockFlow();
		entity.setStockFlowType(StockFlowType.PURCHASED);
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(2);
		detail.getStockProduct().setStockLevel(2);
		entity.setDetails(Arrays.asList(detail));
		StockFlowDto dto = StockFlowDtoFixture.getStockFlowDto();

		when(stockFlowCommandToEntityMapper.map(command)).thenReturn(entity);
		when(dtoMapper.map(entity)).thenReturn(dto);

		stockFlowService.save(command, UserFixture.getCateringAdmin(), dtoMapper);

		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(4);
	}

	@Test
	@Ignore
	public void testSave__UpdateCorrectStock__NoRemainingConsumptions() throws Exception {
		StockFlowCommand command = StockFlowCommandFixture.mutable();
		StockFlow entity = StockFlowFixture.getStockFlow();
		entity.setStockFlowType(StockFlowType.PURCHASED);
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(2);
		detail.getStockProduct().setRemainingConsumptions(0);
		detail.getStockProduct().setStockLevel(2);
		entity.setDetails(Arrays.asList(detail));
		StockFlowDto dto = StockFlowDtoFixture.getStockFlowDto();

		when(stockFlowCommandToEntityMapper.map(command)).thenReturn(entity);
		when(dtoMapper.map(entity)).thenReturn(dto);

		stockFlowService.save(command, UserFixture.getCateringAdmin(), dtoMapper);

		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(3);
	}

	@Test
	@Ignore
	public void testSave__UpdateCorrectStock__RemainingConsumptions() throws Exception {
		StockFlowCommand command = StockFlowCommandFixture.mutable();
		StockFlow entity = StockFlowFixture.getStockFlow();
		entity.setStockFlowType(StockFlowType.PURCHASED);
		StockFlowDetail detail = StockFlowFixture.getStockFlowDetail();
		detail.setQuantity(2);
		detail.getStockProduct().setRemainingConsumptions(1);
		detail.getStockProduct().setStockLevel(2);
		entity.setDetails(Arrays.asList(detail));
		StockFlowDto dto = StockFlowDtoFixture.getStockFlowDto();

		when(stockFlowCommandToEntityMapper.map(command)).thenReturn(entity);
		when(dtoMapper.map(entity)).thenReturn(dto);

		stockFlowService.save(command, UserFixture.getCateringAdmin(), dtoMapper);

		assertThat(detail.getStockProduct().getStockLevel()).isEqualTo(4);
	}
}