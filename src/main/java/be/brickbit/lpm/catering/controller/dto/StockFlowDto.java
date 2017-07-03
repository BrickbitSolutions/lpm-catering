package be.brickbit.lpm.catering.controller.dto;

import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.domain.StockCorrectionLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StockFlowDto {
    private Long id;
    private String username;
    private StockFlowType type;
    private StockCorrectionLevel level;
    private String timestamp;
    private List<StockFlowDetailDto> stockFlowDetails;
}
