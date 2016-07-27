package be.brickbit.lpm.catering.service.stockproduct.mapper;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class StockProductCommandToEntityMapper implements Mapper<StockProductCommand, StockProduct> {
    @Override
    public StockProduct map(StockProductCommand source) {
        StockProduct stockProduct = new StockProduct();

        stockProduct.setName(source.getName());
        stockProduct.setMaxConsumptions(source.getMaxConsumptions());
        stockProduct.setClearance(source.getClearance());
        stockProduct.setProductType(source.getProductType());

        if(source.getStockLevel() > 0){
            stockProduct.setRemainingConsumptions(source.getMaxConsumptions());
            if(source.getMaxConsumptions() > 1){
                stockProduct.setStockLevel(source.getStockLevel() - 1);
            }else{
                stockProduct.setStockLevel(source.getStockLevel());
            }
        }else{
            stockProduct.setRemainingConsumptions(0);
            stockProduct.setStockLevel(0);
        }

        return stockProduct;
    }
}
