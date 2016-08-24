package be.brickbit.lpm.catering.service.product.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReceiptLineCommand {
    @NotNull(message = "Stock product is required.")
    private Long stockProductId;
    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity cannot be 0 or lower.")
    private Integer quantity;
}
