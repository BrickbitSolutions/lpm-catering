package be.brickbit.lpm.catering.service.stockflow.command;

import be.brickbit.lpm.catering.domain.StockFlowType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class StockFlowCommand {
    @NotNull
    private StockFlowType stockFlowType;
    @Valid
    @NotNull
    private List<StockFlowDetailCommand> stockFlowDetails;

    public StockFlowType getStockFlowType() {
        return stockFlowType;
    }

    public void setStockFlowType(StockFlowType someStockFlowType) {
        stockFlowType = someStockFlowType;
    }

    public List<StockFlowDetailCommand> getStockFlowDetails() {
        return stockFlowDetails;
    }

    public void setStockFlowDetails(List<StockFlowDetailCommand> stockFlowDetails) {
        this.stockFlowDetails = stockFlowDetails;
    }
}