package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.stockcorrection.command.StockCorrectionCommand;

public class StockCorrectionCommandFixture {
    public static StockCorrectionCommand getNewStockCorrectionCommand(){
        StockCorrectionCommand command = new StockCorrectionCommand();

        command.setQuantity(-1);
        command.setMessage("Crew Consumption");
        command.setStockProductId(1L);

        return command;
    }
}
