package be.brickbit.lpm.catering.domain;

import lombok.Data;

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
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PRODUCT_ORDER")
public @Data class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "PLACED_BY_USER_ID")
    private Long placedByUserId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private List<OrderLine> orderLines;

    @Column(name = "COMMENT")
    private String comment;
}
