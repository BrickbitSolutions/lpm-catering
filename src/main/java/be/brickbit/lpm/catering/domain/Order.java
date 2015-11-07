package be.brickbit.lpm.catering.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PRODUCT_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIME_ON_ORDER")
    private LocalDateTime timestamp;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "PLACED_BY_USER_ID")
    private Long placedByUserId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private List<OrderLine> orderLines;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlacedByUserId() {
        return placedByUserId;
    }

    public void setPlacedByUserId(Long placedByUserId) {
        this.placedByUserId = placedByUserId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
