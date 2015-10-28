package be.brickbit.lpm.catering.service.stockflow.command;

import be.brickbit.lpm.catering.domain.StockFlowType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockFlowCommand {
    @NotNull(message = "Quantity is required.")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
    @NotNull
    @Min(value = 0, message = "price cannot be negative.")
    private BigDecimal pricePerUnit;
    @NotNull
    private StockFlowType stockFlowType;
    @NotNull
    private Long stockProductId;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer someQuantity) {
        quantity = someQuantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal somePricePerUnit) {
        pricePerUnit = somePricePerUnit;
    }

    public StockFlowType getStockFlowType() {
        return stockFlowType;
    }

    public void setStockFlowType(StockFlowType someStockFlowType) {
        stockFlowType = someStockFlowType;
    }

    public Long getStockProductId() {
        return stockProductId;
    }

    public void setStockProductId(Long someStockProductId) {
        stockProductId = someStockProductId;
    }
}
