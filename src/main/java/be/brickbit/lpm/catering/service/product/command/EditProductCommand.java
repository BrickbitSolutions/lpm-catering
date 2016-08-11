package be.brickbit.lpm.catering.service.product.command;

import be.brickbit.lpm.catering.domain.ClearanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditProductCommand {
    private String name;
    private ClearanceType clearance;
}
