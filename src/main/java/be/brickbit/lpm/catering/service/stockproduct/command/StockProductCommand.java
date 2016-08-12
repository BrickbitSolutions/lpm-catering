package be.brickbit.lpm.catering.service.stockproduct.command;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StockProductCommand {
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
}
