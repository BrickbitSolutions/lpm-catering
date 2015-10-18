package be.brickbit.lpm.catering.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "PRODUCT_TYPE")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "CLEARANCE")
    @Enumerated(EnumType.STRING)
    private ClearanceType clearance;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, unique = true)
    private List<ProductReceiptLine> receipt;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal somePrice) {
        price = somePrice;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType someProductType) {
        productType = someProductType;
    }
}
