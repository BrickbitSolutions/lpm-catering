package be.brickbit.lpm.catering.service.product.command;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ReceiptLineCommand {
    @NotNull(message = "Stock product is required.")
    private Long stockProductId;
    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity cannot be 0 or lower.")
    private Integer quantity;

    public Long getStockProductId() {
        return stockProductId;
    }

    public void setStockProductId(Long someStockProductId) {
        stockProductId = someStockProductId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer someQuantity) {
        quantity = someQuantity;
    }
}
