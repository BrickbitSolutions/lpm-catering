package be.brickbit.lpm.catering.service.order.dto;

import be.brickbit.lpm.catering.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private Long id;
    private BigDecimal totalPrice;
    private String timestamp;
    private String username;
    private OrderStatus status;
    private List<OrderLineDto> orderLines;

    public OrderDto(Long id, BigDecimal totalPrice, String timestamp, String username, OrderStatus status,
                    List<OrderLineDto> orderLines) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
        this.username = username;
        this.status = status;
        this.orderLines = orderLines;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderLineDto> getOrderLines() {
        return orderLines;
    }
}
