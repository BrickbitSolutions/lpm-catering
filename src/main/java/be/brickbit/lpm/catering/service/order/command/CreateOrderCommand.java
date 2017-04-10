package be.brickbit.lpm.catering.service.order.command;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import be.brickbit.lpm.catering.controller.validator.LocalDateFuture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderCommand {
    private Long userId;
    @Valid
    @NotNull(message = "Orderlines may not be null")
    @Size(min = 1, message = "Order must have orderlines")
    private List<OrderLineCommand> orderLines;
    private String comment;
    @LocalDateFuture(message = "Hold Until date must be in the future")
    private LocalDate holdUntil;
}
