package be.brickbit.lpm.catering.service.stockflow.dto;

import be.brickbit.lpm.catering.domain.StockFlowType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class StockFlowDto {
    private Long id;
    private String username;
    private StockFlowType type;
    private LocalDateTime timestamp;
    private List<StockFlowDetailDto> stockFlowDetails;
}
