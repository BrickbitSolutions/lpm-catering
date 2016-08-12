package be.brickbit.lpm.catering.service.stockflow.command;

import be.brickbit.lpm.catering.domain.StockFlowType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductStockFlowCommand {
    @NotNull
    private StockFlowType stockFlowType;
    @NotNull
    private Long productId;
    @NotNull(message = "Quantity is required.")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
    @NotNull
    @Min(value = 0, message = "price cannot be negative.")
    private BigDecimal pricePerUnit;
}
