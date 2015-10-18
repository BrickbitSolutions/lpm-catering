package be.brickbit.lpm.catering.service.stockproduct.command;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SaveStockProductCommand {
    @NotBlank(message = "name cannot be empty.")
    private String name;
    @NotNull(message = "stockLevel must be set.")
    @Min(value = 0, message = "Stocklevel cannot be negative.")
    private Integer stockLevel;
    @NotNull(message = "mex consumptions for the product must be set.")
    @Min(value = 1, message = "max consumptions cannot be negative or zero")
    private Integer maxConsumptions;
    @NotNull(message = "Clearance must be set.")
    private ClearanceType clearance;
    @NotNull(message = "Product Type must be set.")
    private ProductType productType;

    public String getName() {
        return name;
    }

    public void setName(String someName) {
        name = someName;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(Integer someStockLevel) {
        stockLevel = someStockLevel;
    }

    public Integer getMaxConsumptions() {
        return maxConsumptions;
    }

    public void setMaxConsumptions(Integer someMaxConsumptions) {
        maxConsumptions = someMaxConsumptions;
    }

    public ClearanceType getClearance() {
        return clearance;
    }

    public void setClearance(ClearanceType someClearance) {
        clearance = someClearance;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType someProductType) {
        productType = someProductType;
    }
}
