package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.stockcorrection.command.NewStockCorrectionCommand;

public class StockCorrectionCommandFixture {
    public static NewStockCorrectionCommand getNewStockCorrectionCommand(){
        NewStockCorrectionCommand command = new NewStockCorrectionCommand();

        command.setQuantity(-1);
        command.setMessage("Crew Consumption");
        command.setStockProductId(1L);

        return command;
    }
}
