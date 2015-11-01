package be.brickbit.lpm.catering.service.order.command;

public class OrderLineCommand {
    private Integer quanity;
    private Long productId;

    public Integer getQuanity() {
        return quanity;
    }

    public void setQuanity(Integer quanity) {
        this.quanity = quanity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
