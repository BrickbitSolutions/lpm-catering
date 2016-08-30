package be.brickbit.lpm.catering.service.stockflow;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowMapper;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;
import be.brickbit.lpm.infrastructure.Service;

public interface IStockFlowService extends Service<StockFlow> {
    <T> T save(StockFlowCommand command, UserPrincipalDto someUser, StockFlowMapper<T> dtoMapper);
}
