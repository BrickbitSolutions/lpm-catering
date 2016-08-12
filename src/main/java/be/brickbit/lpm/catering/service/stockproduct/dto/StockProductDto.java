package be.brickbit.lpm.catering.service.stockproduct.dto;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private ClearanceType clearance;
    private ProductType productType;
}
