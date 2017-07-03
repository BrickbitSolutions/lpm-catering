package be.brickbit.lpm.catering.controller.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.controller.dto.StockFlowDetailDto;
import be.brickbit.lpm.catering.controller.dto.StockFlowDto;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowMapper;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.client.UserService;

@Component
public class StockFlowDtoMapper implements StockFlowMapper<StockFlowDto> {
    @Autowired
    private UserService userService;

    @Override
    public StockFlowDto map(StockFlow someStockFlow) {
        return new StockFlowDto(
                someStockFlow.getId(),
                userService.findOne(someStockFlow.getUserId()).getUsername(),
                someStockFlow.getStockFlowType(),
                someStockFlow.getLevel(),
                someStockFlow.getTimestamp().format(DateUtils.getDateTimeFormat()),
                someStockFlow.getDetails().stream().map(this::mapDetail).collect(Collectors.toList())
        );
    }

    private StockFlowDetailDto mapDetail(StockFlowDetail detail) {
        return new StockFlowDetailDto(detail.getStockProduct().getName(), detail.getQuantity());
    }
}
