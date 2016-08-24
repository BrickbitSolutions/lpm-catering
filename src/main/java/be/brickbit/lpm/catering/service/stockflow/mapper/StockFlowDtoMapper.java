package be.brickbit.lpm.catering.service.stockflow.mapper;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDetailDto;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.catering.service.user.mapper.UserDtoMapper;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StockFlowDtoMapper implements StockFlowMapper<StockFlowDto> {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Override
    public StockFlowDto map(StockFlow someStockFlow) {
        return new StockFlowDto(
                someStockFlow.getId(),
                userService.findOne(someStockFlow.getUserId(), userDtoMapper).getUsername(),
                someStockFlow.getStockFlowType(),
                someStockFlow.getLevel(),
                someStockFlow.getTimestamp().format(DateUtils.getDateFormat()),
                someStockFlow.getDetails().stream().map(this::mapDetail).collect(Collectors.toList())
        );
    }

    private StockFlowDetailDto mapDetail(StockFlowDetail detail){
        return new StockFlowDetailDto(detail.getStockProduct().getName(),detail.getQuantity());
    }
}
