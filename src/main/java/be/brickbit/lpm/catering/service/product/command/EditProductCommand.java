package be.brickbit.lpm.catering.service.product.command;

import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditProductCommand {
    @NotBlank(message = "Name is required.")
    private String name;
    @NotNull(message = "Price is required.")
    private BigDecimal price;
    @NotNull(message = "Reservation Only is required.")
    private Boolean reservationOnly;
    private List<Long> supplements;
}
