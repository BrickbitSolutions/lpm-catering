package be.brickbit.lpm.catering.service.stockflow.mapper;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StockFlowCommandToEntityMapper implements Mapper<StockFlowCommand, StockFlow> {

    @Autowired
    private StockFlowDetailCommandToEntityMapper detailMapper;

    @Override
    public StockFlow map(StockFlowCommand someStockFlowCommand) {
            StockFlow stockFlow = new StockFlow();

            stockFlow.setStockFlowType(someStockFlowCommand.getStockFlowType());
            stockFlow.setTimestamp(LocalDateTime.now());
            stockFlow.setIncluded(false);
            stockFlow.setDetails(someStockFlowCommand.getStockFlowDetails().stream().map(detailMapper::map).collect(Collectors.toList()));

            return stockFlow;
    }
}
