package be.brickbit.lpm.catering.service.stockproduct.dto;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;

public class StockProductDto {
    private Long id;
    private String name;
    private Integer consumptionsLeft;
    private Integer maxConsumptions;
    private Integer stockLevel;
    private ClearanceType clearance;
    private ProductType productType;

    public StockProductDto(Long someId, String someName, Integer someConsumptionsLeft, Integer someMaxConsumptions, Integer someStockLevel,
                           ClearanceType someClearance, ProductType someProductType) {
        id = someId;
        name = someName;
        consumptionsLeft = someConsumptionsLeft;
        maxConsumptions = someMaxConsumptions;
        stockLevel = someStockLevel;
        clearance = someClearance;
        productType = someProductType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getConsumptionsLeft() {
        return consumptionsLeft;
    }

    public Integer getMaxConsumptions() {
        return maxConsumptions;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    public ClearanceType getClearance() {
        return clearance;
    }

    public ProductType getProductType() {
        return productType;
    }
}
