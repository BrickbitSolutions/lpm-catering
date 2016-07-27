package be.brickbit.lpm.catering.fixture;

import java.time.LocalDateTime;
import java.util.Collections;

import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDetailDto;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.catering.util.DateUtils;

public class StockFlowDtoFixture {
	public static StockFlowDto getStockFlowDto() {
		return new StockFlowDto(
				1L,
				"jay",
				StockFlowType.PURCHASED,
				LocalDateTime.now().format(DateUtils.getDateFormat()),
				Collections.singletonList(new StockFlowDetailDto("Jupiler 33cl", 10)));
	}
}
