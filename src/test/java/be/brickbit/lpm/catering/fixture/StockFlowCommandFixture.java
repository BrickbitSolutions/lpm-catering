package be.brickbit.lpm.catering.fixture;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;

import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.domain.StockCorrectionLevel;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;

public class StockFlowCommandFixture {
	public static StockFlowCommand mutable() {
		return new StockFlowCommand(
				StockFlowType.PURCHASED,
				randomLong(),
				StockCorrectionLevel.STOCK,
				randomInt());
	}
}
