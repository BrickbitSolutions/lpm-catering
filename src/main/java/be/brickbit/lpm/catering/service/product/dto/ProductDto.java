package be.brickbit.lpm.catering.service.product.dto;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProductType productType;
    private ClearanceType clearanceType;

    public ProductDto(Long someId, String someName, BigDecimal somePrice, ProductType someProductType, ClearanceType
            someClearanceType) {
        id = someId;
        name = someName;
        price = somePrice;
        productType = someProductType;
        clearanceType = someClearanceType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public ClearanceType getClearanceType() {
        return clearanceType;
    }

}
