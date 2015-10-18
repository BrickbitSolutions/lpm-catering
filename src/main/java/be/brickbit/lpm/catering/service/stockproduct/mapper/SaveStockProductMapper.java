package be.brickbit.lpm.catering.service.stockproduct.mapper;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.service.AbstractMapper;
import be.brickbit.lpm.catering.service.stockproduct.command.SaveStockProductCommand;
import org.springframework.stereotype.Component;

@Component
public class SaveStockProductMapper extends AbstractMapper<SaveStockProductCommand, StockProduct> {
    @Override
    public StockProduct map(SaveStockProductCommand source) {
        StockProduct stockProduct = new StockProduct();

        stockProduct.setName(source.getName());
        stockProduct.setMaxConsumptions(source.getMaxConsumptions());
        stockProduct.setStockLevel(source.getStockLevel());
        stockProduct.setClearance(source.getClearance());
        stockProduct.setProductType(source.getProductType());

        if(source.getStockLevel() > 0){
            stockProduct.setRemainingConsumptions(source.getMaxConsumptions());
        }else{
            stockProduct.setRemainingConsumptions(0);
        }

        return stockProduct;
    }
}
