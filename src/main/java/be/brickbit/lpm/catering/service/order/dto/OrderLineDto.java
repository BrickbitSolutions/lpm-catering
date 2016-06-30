package be.brickbit.lpm.catering.service.order.dto;

import be.brickbit.lpm.catering.domain.OrderStatus;

public class OrderLineDto {
    private Integer quantity;
    private String product;
    private OrderStatus status;

    public OrderLineDto(Integer quantity, String product, OrderStatus status) {
        this.quantity = quantity;
        this.product = product;
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getProduct() {
        return product;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
