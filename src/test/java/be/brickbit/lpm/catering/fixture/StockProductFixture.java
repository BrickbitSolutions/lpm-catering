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

    public static StockProduct getStockProductDuvel(){
        StockProduct stockProduct = new StockProduct();

        stockProduct.setId(2L);
        stockProduct.setStockLevel(20);
        stockProduct.setProductType(ProductType.DRINKS);
        stockProduct.setClearance(ClearanceType.PLUS_18);
        stockProduct.setName("Duvel 33cl");
        stockProduct.setMaxConsumptions(1);
        stockProduct.setRemainingConsumptions(1);

        return stockProduct;
    }

    public static StockProduct getStockProductPizza(){
        StockProduct stockProduct = new StockProduct();

        stockProduct.setId(3L);
        stockProduct.setStockLevel(20);
        stockProduct.setProductType(ProductType.FOOD);
        stockProduct.setClearance(ClearanceType.ANY);
        stockProduct.setName("Pizza Margarita");
        stockProduct.setMaxConsumptions(1);
        stockProduct.setRemainingConsumptions(1);

        return stockProduct;
    }
}
