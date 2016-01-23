package be.brickbit.lpm.catering.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "STOCK_CORRECTION")
public class StockCorrection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "CORRECTION_TIME")
    private LocalDateTime timestamp;

    @Column(name = "USER_ID")
    private Long userId;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String someMessage) {
        message = someMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime someTimestamp) {
        timestamp = someTimestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long someUserId) {
        userId = someUserId;
    }

    public StockProduct getStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(StockProduct someStockProduct) {
        stockProduct = someStockProduct;
    }
}
