package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowDetailCommand;

import java.math.BigDecimal;
import java.util.Collections;

public class StockFlowCommandFixture {
    public static StockFlowCommand getStockFlowCommand(){
        StockFlowCommand stockFlowCommand = new StockFlowCommand();

        stockFlowCommand.setStockFlowType(StockFlowType.PURCHASED);
        stockFlowCommand.setStockFlowDetails(Collections.singletonList(getStockFlowDetailCommand()));

        return stockFlowCommand;
    }

    private static StockFlowDetailCommand getStockFlowDetailCommand() {
        StockFlowDetailCommand stockFlowDetailCommand = new StockFlowDetailCommand();

        stockFlowDetailCommand.setPricePerUnit(BigDecimal.TEN);
        stockFlowDetailCommand.setQuantity(5);
        stockFlowDetailCommand.setStockProductId(1L);

        return stockFlowDetailCommand;
    }
}
