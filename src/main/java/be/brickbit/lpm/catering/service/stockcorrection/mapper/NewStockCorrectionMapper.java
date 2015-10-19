package be.brickbit.lpm.catering.service.stockcorrection.mapper;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.AbstractMapper;
import be.brickbit.lpm.catering.service.stockcorrection.command.NewStockCorrectionCommand;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class NewStockCorrectionMapper extends AbstractMapper<NewStockCorrectionCommand, StockCorrection> {

    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    public StockCorrection map(NewStockCorrectionCommand command) {
        Optional<StockProduct> stockProduct = Optional.of(stockProductRepository.findOne(command.getStockProductId()));

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
