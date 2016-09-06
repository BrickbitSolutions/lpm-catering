package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class StockProductCommandFixture {
    public static StockProductCommand mutable(){
        StockProductCommand command = new StockProductCommand();

        command.setProductType(ProductType.DRINKS);
        command.setClearance(ClearanceType.PLUS_16);
        command.setStockLevel(randomInt(1, 999));
        command.setMaxConsumptions(1);
        command.setName(randomString());

        return command;
    }
}
