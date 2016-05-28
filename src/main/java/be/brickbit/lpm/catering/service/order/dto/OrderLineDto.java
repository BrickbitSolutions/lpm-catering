package be.brickbit.lpm.catering.service.order.dto;

public class OrderLineDto {
    private Integer quantity;
    private String product;

    public OrderLineDto(Integer quantity, String product) {
        this.quantity = quantity;
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getProduct() {
        return product;
    }
}
