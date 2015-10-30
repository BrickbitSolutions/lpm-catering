package be.brickbit.lpm.catering.service.product.command;

import be.brickbit.lpm.catering.domain.ProductType;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class ProductCommand {
    @NotBlank(message = "Name is required.")
    private String name;
    @NotNull(message = "Price is required.")
    private BigDecimal price;
    @NotNull(message = "Product type is required.")
    private ProductType productType;
    @NotNull(message = "Product needs stock products.")
    @Valid
    private List<ReceiptLineCommand> receipt;
    @Min(value = 0, message = "Time cannot be negative.")
    private Integer timerInMinutes;
    private String queueName;
    private String instructions;

    public String getName() {
        return name;
    }

    public void setName(String someName) {
        name = someName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal somePrice) {
        price = somePrice;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType someProductType) {
        productType = someProductType;
    }

    public List<ReceiptLineCommand> getReceipt() {
        return receipt;
    }

    public void setReceipt(List<ReceiptLineCommand> someReceipt) {
        receipt = someReceipt;
    }

    public Integer getTimerInMinutes() {
        return timerInMinutes;
    }

    public void setTimerInMinutes(Integer someTimerInMinutes) {
        timerInMinutes = someTimerInMinutes;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String someQueueName) {
        queueName = someQueueName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String someInstructions) {
        instructions = someInstructions;
    }
}
