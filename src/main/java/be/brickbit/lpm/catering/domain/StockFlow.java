package be.brickbit.lpm.catering.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "STOCK_FLOW")
public class StockFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

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
