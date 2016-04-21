package be.brickbit.lpm.catering.domain;

import lombok.Data;

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
public @Data class StockProduct {
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
}
