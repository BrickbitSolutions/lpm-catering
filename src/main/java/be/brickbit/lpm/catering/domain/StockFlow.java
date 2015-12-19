package be.brickbit.lpm.catering.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany
    @JoinColumn(name = "STOCK_FLOW_ID")
    private List<StockFlowDetail> details;

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

    public List<StockFlowDetail> getDetails() {
        return details;
    }

    public void setDetails(List<StockFlowDetail> details) {
        this.details = details;
    }
}
