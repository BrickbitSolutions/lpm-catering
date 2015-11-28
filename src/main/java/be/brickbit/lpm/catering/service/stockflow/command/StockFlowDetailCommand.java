package be.brickbit.lpm.catering.service.stockflow.command;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class StockFlowDetailCommand {
    @NotNull
    private Long stockProductId;
    @NotNull(message = "Quantity is required.")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Long quantity;
    @NotNull
    @Min(value = 0, message = "price cannot be negative.")
    private BigDecimal pricePerUnit;

    public Long getStockProductId() {
        return stockProductId;
    }

    public void setStockProductId(Long stockProductId) {
        this.stockProductId = stockProductId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
