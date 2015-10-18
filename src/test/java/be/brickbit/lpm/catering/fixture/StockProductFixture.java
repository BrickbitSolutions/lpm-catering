package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.domain.StockProduct;

public class StockProductFixture {
    public static StockProduct getStockProduct(){
        StockProduct stockProduct = new StockProduct();

        stockProduct.setId(1L);
        stockProduct.setStockLevel(20);
        stockProduct.setProductType(ProductType.DRINKS);
        stockProduct.setClearance(ClearanceType.PLUS_16);
        stockProduct.setName("Jupiler 33cl");
        stockProduct.setMaxConsumptions(1);
        stockProduct.setRemainingConsumptions(1);

        return stockProduct;
    }
}
