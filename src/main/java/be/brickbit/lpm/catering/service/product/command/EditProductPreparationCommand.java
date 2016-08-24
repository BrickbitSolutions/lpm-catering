package be.brickbit.lpm.catering.service.product.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditProductPreparationCommand {
    @NotBlank
    private String queueName;
    @Min(value = 0, message = "Time cannot be negative.")
    private Integer timerInMinutes;
    private String instructions;
}
