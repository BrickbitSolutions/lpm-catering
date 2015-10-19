package be.brickbit.lpm.catering.service.stockcorrection.command;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class NewStockCorrectionCommand {
    @NotNull(message = "Product Id is required.")
    private Long stockProductId;
    @NotBlank(message = "Message is required.")
    private String message;
    @NotNull(message = "Quantity is required.")
    private Integer quantity;

    public Long getStockProductId() {
        return stockProductId;
    }

    public void setStockProductId(Long someStockProductId) {
        stockProductId = someStockProductId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String someMessage) {
        message = someMessage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer someQuantity) {
        quantity = someQuantity;
    }
}
