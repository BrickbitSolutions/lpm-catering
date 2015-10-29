package be.brickbit.lpm.catering.service.product.dto;

public class ReceiptDto {
    private Long stockProductId;
    private Integer quantity;

    public ReceiptDto(Long someStockProductId, Integer someQuantity) {
        stockProductId = someStockProductId;
        quantity = someQuantity;
    }

    public Long getStockProductId() {
        return stockProductId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
