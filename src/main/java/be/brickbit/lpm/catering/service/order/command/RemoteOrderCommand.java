package be.brickbit.lpm.catering.service.order.command;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RemoteOrderCommand {
    @Valid
    @NotNull(message = "Orderlines may not be null")
    @Size(min = 1, message = "Order must have orderlines")
    private List<OrderLineCommand> orderLines;
    private String comment;
}
