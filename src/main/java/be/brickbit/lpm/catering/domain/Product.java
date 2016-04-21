package be.brickbit.lpm.catering.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "PRODUCT")
public @Data class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "AVAILABLE")
    private Boolean available;

    @Column(name = "PRODUCT_TYPE")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "CLEARANCE")
    @Enumerated(EnumType.STRING)
    private ClearanceType clearance;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, unique = true)
    private List<ProductReceiptLine> receipt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PREPARATION_ID", unique = true)
    private ProductPreparation preparation;
}
