package be.brickbit.lpm.catering.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STOCKPRODUCT")
public class StockProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STOCKLEVEL")
    private Integer stockLevel;

    @Column(name = "MAX_CONSUMPTIONS")
    private Integer maxConsumptions;

    @Column(name = "REMAINING_CONSUMPTIONS")
    private Integer remainingConsumptions;

    @Column(name = "CLEARANCE")
    @Enumerated(EnumType.STRING)
    private ClearanceType clearance;

    @Column(name = "PRODUCT_TYPE")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    public Long getId() {
        return id;
    }

    public void setId(Long someId) {
        id = someId;
    }

    public String getName() {
        return name;
    }

    public void setName(String someName) {
        name = someName;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(Integer someStockLevel) {
        stockLevel = someStockLevel;
    }

    public Integer getMaxConsumptions() {
        return maxConsumptions;
    }

    public void setMaxConsumptions(Integer someMaxConsumptions) {
        maxConsumptions = someMaxConsumptions;
    }

    public Integer getRemainingConsumptions() {
        return remainingConsumptions;
    }

    public void setRemainingConsumptions(Integer someRemainingConsumptions) {
        remainingConsumptions = someRemainingConsumptions;
    }

    public ClearanceType getClearance() {
        return clearance;
    }

    public void setClearance(ClearanceType someClearance) {
        clearance = someClearance;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType someProductType) {
        productType = someProductType;
    }
}
