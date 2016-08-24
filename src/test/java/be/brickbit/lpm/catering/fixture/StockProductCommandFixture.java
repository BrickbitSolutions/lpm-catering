package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;

public class StockProductCommandFixture {
    public static StockProductCommand getSaveCommand(){
        StockProductCommand command = new StockProductCommand();

        command.setProductType(ProductType.DRINKS);
        command.setClearance(ClearanceType.PLUS_16);
        command.setStockLevel(0);
        command.setMaxConsumptions(1);
        command.setName("Jupiler 33cl");

        return command;
    }
}
