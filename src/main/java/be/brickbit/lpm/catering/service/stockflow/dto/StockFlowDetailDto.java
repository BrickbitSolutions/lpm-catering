package be.brickbit.lpm.catering.service.stockflow.dto;

import java.math.BigDecimal;

public class StockFlowDetailDto {
    private String productName;
    private Long quantity;
    private BigDecimal pricePerUnit;

    public StockFlowDetailDto(String productName, Long quantity, BigDecimal pricePerUnit) {
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public String getProductName() {
        return productName;
    }
}
