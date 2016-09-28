package be.brickbit.lpm.catering.service.order.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DirectOrderCommand {
    @NotNull(message = "User ID cannot be empty.")
    private Long userId;
    @Valid
    @NotNull(message = "Orderlines may not be null")
    @Size(min = 1, message = "Order must have orderlines")
    private List<OrderLineCommand> orderLines;
    private String comment;
}
