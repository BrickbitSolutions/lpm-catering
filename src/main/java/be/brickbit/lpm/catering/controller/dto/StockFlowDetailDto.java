package be.brickbit.lpm.catering.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockFlowDetailDto {
	private String productName;
	private Integer quantity;
}
