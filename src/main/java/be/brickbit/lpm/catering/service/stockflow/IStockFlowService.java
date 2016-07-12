package be.brickbit.lpm.catering.service.stockflow;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.service.stockflow.command.ProductStockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowMapper;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.Service;

public interface IStockFlowService extends Service<StockFlow> {
    <T> T save(StockFlowCommand command, User someUser, StockFlowMapper<T> dtoMapper);
}
