package be.brickbit.lpm.catering.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_RECEIPT_LINE")
public @Data class ProductReceiptLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "STOCK_PRODUCT_ID", nullable = false)
    public StockProduct stockProduct;

    @Column(name = "QUANTITY")
    public Integer quantity;
}
