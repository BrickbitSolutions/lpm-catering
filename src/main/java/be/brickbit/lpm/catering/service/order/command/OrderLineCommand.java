package be.brickbit.lpm.catering.service.order.command;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLineCommand {
    @NotNull(message = "Quantity cannot be empty.")
    @Min(value = 1, message = "Quantity cannot be negative or 0.")
    private Integer quantity;

    @NotNull(message = "ProductId cannot be empty.")
    private Long productId;

    private List<Long> supplements;
}
