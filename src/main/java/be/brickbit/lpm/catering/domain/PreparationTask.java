package be.brickbit.lpm.catering.domain;

import be.brickbit.lpm.core.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PREPARATION_TASK")
public class PreparationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "QUEUE_TIME")
    private LocalDateTime queueTime;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "STATUS")
    private OrderStatus status;

    @OneToOne
    @JoinColumn(name = "ORDERLINE_ID")
    private OrderLine orderLine;

    //@ManyToOne
    //@JoinColumn(name = "USER_ID")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(LocalDateTime queueTime) {
        this.queueTime = queueTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(OrderLine orderLine) {
        this.orderLine = orderLine;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
