package be.brickbit.lpm.catering.controller.dto;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockProductDto {
    private Long id;
    private String name;
    private Integer consumptionsLeft;
    private Integer maxConsumptions;
    private Integer avgConsumption;
    private Integer stockLevel;
    private ClearanceType clearanceType;
    private ProductType productType;
}
