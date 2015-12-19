package be.brickbit.lpm.catering.service.stockflow.dto;

import java.math.BigDecimal;

public class StockFlowDetailDto {
    private String productName;
    private Integer quantity;
    private BigDecimal pricePerUnit;

    public StockFlowDetailDto(String productName, Integer quantity, BigDecimal pricePerUnit) {
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public String getProductName() {
        return productName;
    }
}
