package be.brickbit.lpm.catering.service.stockflow.dto;

import be.brickbit.lpm.catering.domain.StockFlowType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class StockFlowDto {
    private Long id;
    private String username;
    private StockFlowType type;
    private Boolean included;
    private LocalDateTime timestamp;
    private List<StockFlowDetailDto> stockFlowDetails;

    public StockFlowDto(Long id, String username, StockFlowType type, Boolean included, LocalDateTime timestamp,
                        List<StockFlowDetailDto> stockFlowDetails) {
        this.id = id;
        this.username = username;
        this.type = type;
        this.included = included;
        this.timestamp = timestamp;
        this.stockFlowDetails = stockFlowDetails;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public StockFlowType getType() {
        return type;
    }

    public Boolean getIncluded() {
        return included;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<StockFlowDetailDto> getStockFlowDetails() {
        return stockFlowDetails;
    }
}
