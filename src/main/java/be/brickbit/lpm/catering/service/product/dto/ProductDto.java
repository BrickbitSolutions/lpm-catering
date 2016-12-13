package be.brickbit.lpm.catering.service.product.dto;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProductType productType;
    private ClearanceType clearanceType;
    private Integer avgConsumption;
    private Integer stockLevel;
    private Boolean available;
    private Boolean reservationOnly;
    private List<SupplementDto> supplements;
}
