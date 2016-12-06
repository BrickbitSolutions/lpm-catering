package be.brickbit.lpm.catering.service.order.dto;

import java.math.BigDecimal;
import java.util.List;

import be.brickbit.lpm.catering.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDetailDto {
	private Long id;
	private BigDecimal totalPrice;
	private String timestamp;
    private Long userId;
	private OrderStatus status;
	private List<OrderLineDto> orderLines;
	private String comment;
	private ReservationDetailDto reservationDetails;
}
