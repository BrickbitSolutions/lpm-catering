package be.brickbit.lpm.catering.service.stockflow.command;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import be.brickbit.lpm.catering.domain.StockFlowType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockFlowCommand {
	@NotNull
	private StockFlowType stockFlowType;
	@NotNull
	private Long productId;
	@NotNull
	private StockCorrectionLevel level;
	@NotNull(message = "Quantity is required.")
	@Min(value = 0, message = "Quantity cannot be negative")
	private Integer quantity;
}