package be.brickbit.lpm.catering.service.stockcorrection.dto;

import java.time.LocalDateTime;

public class StockCorrectionDto {
    private String productName;
    private Integer quantity;
    private LocalDateTime timestamp;
    private String userName;

    public StockCorrectionDto(String someProductName, Integer someQuantity, LocalDateTime someTimestamp, String
            someUserName) {
        productName = someProductName;
        quantity = someQuantity;
        timestamp = someTimestamp;
        userName = someUserName;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUserName() {
        return userName;
    }
}
