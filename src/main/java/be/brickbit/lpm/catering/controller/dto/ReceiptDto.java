package be.brickbit.lpm.catering.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReceiptDto {
    private Long stockProductId;
    private Integer quantity;
}
