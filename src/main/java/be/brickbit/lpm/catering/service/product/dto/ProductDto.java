package be.brickbit.lpm.catering.service.product.dto;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProductType productType;
    private ClearanceType clearanceType;
    private List<ReceiptDto> receipt;

    public ProductDto(Long someId, String someName, BigDecimal somePrice, ProductType someProductType, ClearanceType
            someClearanceType, List<ReceiptDto> someReceipt) {
        id = someId;
        name = someName;
        price = somePrice;
        productType = someProductType;
        clearanceType = someClearanceType;
        receipt = someReceipt;
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

    public List<ReceiptDto> getReceipt() {
        return receipt;
    }
}
