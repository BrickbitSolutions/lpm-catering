package be.brickbit.lpm.catering.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "STOCK_FLOW")
public class StockFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE_PER_UNIT")
    private BigDecimal pricePerUnit;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private StockFlowType stockFlowType;

    @Column(name = "INCLUDED")
    private Boolean included;

    @Column(name = "TIME_ON_ENTRY")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "STOCK_PRODUCT_ID")
    private StockProduct stockProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long someId) {
        id = someId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer someQuantity) {
        quantity = someQuantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal somePricePerUnit) {
        pricePerUnit = somePricePerUnit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long someUserId) {
        userId = someUserId;
    }

    public StockFlowType getStockFlowType() {
        return stockFlowType;
    }

    public void setStockFlowType(StockFlowType someStockFlowType) {
        stockFlowType = someStockFlowType;
    }

    public Boolean getIncluded() {
        return included;
    }

    public void setIncluded(Boolean someIncluded) {
        included = someIncluded;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime someTimestamp) {
        timestamp = someTimestamp;
    }

    public StockProduct getStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(StockProduct someStockProduct) {
        stockProduct = someStockProduct;
    }
}
