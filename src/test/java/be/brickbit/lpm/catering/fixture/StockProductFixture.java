package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.domain.StockProduct;

public class StockProductFixture {
    public static StockProduct getStockProductJupiler(){
        StockProduct stockProduct = new StockProduct();

        stockProduct.setStockLevel(20);
        stockProduct.setProductType(ProductType.DRINKS);
        stockProduct.setClearance(ClearanceType.PLUS_16);
        stockProduct.setName("Jupiler");
        stockProduct.setMaxConsumptions(1);
        stockProduct.setRemainingConsumptions(1);

        return stockProduct;
    }

    public static StockProduct getStockProductCola(){
        StockProduct stockProduct = new StockProduct();

        stockProduct.setStockLevel(3);
        stockProduct.setProductType(ProductType.DRINKS);
        stockProduct.setClearance(ClearanceType.ANY);
        stockProduct.setName("Cola");
        stockProduct.setMaxConsumptions(1);
        stockProduct.setRemainingConsumptions(1);

        return stockProduct;
    }

    public static StockProduct getStockProductPizza(){
        StockProduct stockProduct = new StockProduct();

        stockProduct.setStockLevel(3);
        stockProduct.setProductType(ProductType.FOOD);
        stockProduct.setClearance(ClearanceType.ANY);
        stockProduct.setName("Pizza");
        stockProduct.setMaxConsumptions(1);
        stockProduct.setRemainingConsumptions(1);

        return stockProduct;
    }

    public static StockProduct getStockProductLasagna(){
        StockProduct stockProduct = new StockProduct();

        stockProduct.setStockLevel(3);
        stockProduct.setProductType(ProductType.FOOD);
        stockProduct.setClearance(ClearanceType.ANY);
        stockProduct.setName("Lasagna");
        stockProduct.setMaxConsumptions(1);
        stockProduct.setRemainingConsumptions(1);

        return stockProduct;
    }
}
