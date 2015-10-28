package be.brickbit.lpm.catering.service.stockflow.dto;

import be.brickbit.lpm.catering.domain.StockFlowType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockFlowDto {
    private Long id;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private String username;
    private StockFlowType type;
    private Boolean included;
    private LocalDateTime timestamp;
    private String productName;

    public StockFlowDto(Long someId, Integer someQuantity, BigDecimal somePricePerUnit, String someUsername,
                        StockFlowType someType, Boolean someIncluded, LocalDateTime someTimestamp, String
                                someProductName) {
        id = someId;
        quantity = someQuantity;
        pricePerUnit = somePricePerUnit;
        username = someUsername;
        type = someType;
        included = someIncluded;
        timestamp = someTimestamp;
        productName = someProductName;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public String getUsername() {
        return username;
    }

    public StockFlowType getType() {
        return type;
    }

    public Boolean getIncluded() {
        return included;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getProductName() {
        return productName;
    }
}
