package be.brickbit.lpm.catering.service.product.command;

import be.brickbit.lpm.catering.domain.ClearanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditProductCommand {
    @NotBlank(message = "Name is required.")
    private String name;
    @NotNull(message = "Clearance is required.")
    private ClearanceType clearance;
}
