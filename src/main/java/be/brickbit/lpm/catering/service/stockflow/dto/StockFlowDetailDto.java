package be.brickbit.lpm.catering.service.stockflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockFlowDetailDto {
	private String productName;
	private Integer quantity;
}
