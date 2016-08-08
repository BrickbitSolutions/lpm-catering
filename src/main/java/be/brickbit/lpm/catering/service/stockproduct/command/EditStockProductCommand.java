package be.brickbit.lpm.catering.service.stockproduct.command;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditStockProductCommand {
	@NotBlank(message = "name cannot be empty.")
	private String name;
	@NotNull(message = "Clearance must be set.")
	private ClearanceType clearance;
	@NotNull(message = "Product Type must be set.")
	private ProductType productType;
}
