package be.brickbit.lpm.catering.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "STOCK_FLOW_DETAIL")
public class StockFlowDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STOCK_PRODUCT_ID")
    private StockProduct stockProduct;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public StockProduct getStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(StockProduct someStockProduct) {
        stockProduct = someStockProduct;
    }
}
