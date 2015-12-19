package be.brickbit.lpm.catering.service.stockflow.mapper;

import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowDetailCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StockFlowDetailCommandToEntityMapper implements Mapper<StockFlowDetailCommand, StockFlowDetail> {
    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    public StockFlowDetail map(StockFlowDetailCommand stockFlowDetailCommand) {

        Optional<StockProduct> stockProduct = Optional.ofNullable(stockProductRepository.findOne(stockFlowDetailCommand.getStockProductId()));

        if (stockProduct.isPresent()) {
            StockFlowDetail stockFlowDetail = new StockFlowDetail();

            stockFlowDetail.setPrice(stockFlowDetailCommand.getPricePerUnit());
            stockFlowDetail.setQuantity(stockFlowDetailCommand.getQuantity());
            stockFlowDetail.setStockProduct(stockProduct.get());

            return stockFlowDetail;
        } else {
            throw new EntityNotFoundException("Invalid stock product");
        }
    }
}
