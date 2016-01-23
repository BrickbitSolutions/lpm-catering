package be.brickbit.lpm.catering.service.stockcorrection.mapper;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockcorrection.command.StockCorrectionCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class StockCorrectionCommandToEntityMapper implements Mapper<StockCorrectionCommand, StockCorrection> {

    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    public StockCorrection map(StockCorrectionCommand command) {
        Optional<StockProduct> stockProduct = Optional.ofNullable(stockProductRepository.findOne(command.getStockProductId()));

        if(stockProduct.isPresent()){
            StockCorrection stockCorrection = new StockCorrection();
            stockCorrection.setMessage(command.getMessage());
            stockCorrection.setQuantity(command.getQuantity());
            stockCorrection.setStockProduct(stockProduct.get());
            stockCorrection.setTimestamp(LocalDateTime.now());
            return stockCorrection;
        }else{
            throw new RuntimeException("Invalid stock product.");
        }
    }
}
