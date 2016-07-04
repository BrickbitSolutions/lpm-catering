package be.brickbit.lpm.catering.service.order.dto;

import be.brickbit.lpm.catering.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderLineDto {
    private Long id;
    private Integer quantity;
    private String product;
    private OrderStatus status;
}
