package be.brickbit.lpm.catering.service.product.command;

import be.brickbit.lpm.catering.domain.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductCommand {
    @NotBlank(message = "Name is required.")
    private String name;
    @NotNull(message = "Price is required.")
    private BigDecimal price;
    @NotNull(message = "Product type is required.")
    private ProductType productType;
    @NotNull(message = "Product needs stock products.")
    @Size(min = 1, message = "Product needs stock products.")
    @Valid
    private List<ReceiptLineCommand> receipt;
    private List<Long> supplements;
    @Min(value = 0, message = "Time cannot be negative.")
    private Integer timerInMinutes;
    private String queueName;
    private String instructions;
}
