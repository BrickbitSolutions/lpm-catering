package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.service.stockflow.command.ProductClass;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;

public class StockFlowCommandFixture {
    public static StockFlowCommand mutable(){
        return new StockFlowCommand(
                StockFlowType.PURCHASED,
                randomLong(),
                ProductClass.STOCKPRODUCT,
                randomInt()
        );
    }
}
