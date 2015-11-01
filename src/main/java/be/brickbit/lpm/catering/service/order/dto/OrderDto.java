package be.brickbit.lpm.catering.service.order.dto;

import be.brickbit.lpm.catering.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {
    private Long id;
    private BigDecimal totalPrice;
    private LocalDateTime timestamp;
    private String username;
    private OrderStatus status;

    public OrderDto(Long id, BigDecimal totalPrice, LocalDateTime timestamp, String username, OrderStatus status) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
        this.username = username;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
