package be.brickbit.lpm.catering.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "PRODUCT_ORDER_LINE")
public
@Data
class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToMany
    @JoinTable(name = "product_order_line_supplement", joinColumns = {
            @JoinColumn(name = "ORDER_LINE_ID", nullable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "STOCK_PRODUCT_ID", nullable = false)
    })
    private List<StockProduct> supplements;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}