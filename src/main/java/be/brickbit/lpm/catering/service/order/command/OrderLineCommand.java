package be.brickbit.lpm.catering.service.order.command;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderLineCommand {
    @NotNull(message = "Quantity cannot be empty.")
    @Min(value = 1, message = "Quantity cannot be negative or 0.")
    private Integer quantity;

    @NotNull(message = "ProductId cannot be empty.")
    private Long productId;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
