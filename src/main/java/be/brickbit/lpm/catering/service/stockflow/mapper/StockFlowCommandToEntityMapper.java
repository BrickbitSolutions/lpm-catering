package be.brickbit.lpm.catering.service.stockflow.mapper;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class StockFlowCommandToEntityMapper implements Mapper<StockFlowCommand, StockFlow> {

    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    public StockFlow map(StockFlowCommand someStockFlowCommand) {

        Optional<StockProduct> stockProduct = Optional.ofNullable(stockProductRepository.findOne(someStockFlowCommand.getStockProductId()));

        if(stockProduct.isPresent()) {
            StockFlow stockFlow = new StockFlow();

            stockFlow.setStockFlowType(someStockFlowCommand.getStockFlowType());
            stockFlow.setTimestamp(LocalDateTime.now());
            stockFlow.setIncluded(false);
            stockFlow.setPricePerUnit(someStockFlowCommand.getPricePerUnit());
            stockFlow.setQuantity(someStockFlowCommand.getQuantity());
            stockFlow.setStockProduct(stockProduct.get());

            return stockFlow;
        }else{
            throw new EntityNotFoundException("Invalid stock product");
        }
    }
}
