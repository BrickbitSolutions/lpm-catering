package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductPreparation;
import be.brickbit.lpm.catering.domain.ProductType;

import java.math.BigDecimal;
import java.util.Collections;

public class ProductFixture {
    public static Product getJupiler(){
        Product product = new Product();

        product.setClearance(ClearanceType.PLUS_16);
        product.setPrice(BigDecimal.ONE);
        product.setProductType(ProductType.DRINKS);
        product.setName("Jupiler");
        product.setReceipt(Collections.singletonList(ProductReceiptLineFixture.getJupiler()));
        product.setAvailable(true);

        return product;
    }

    public static Product getPizza(){
        Product product = new Product();

        product.setClearance(ClearanceType.ANY);
        product.setPrice(BigDecimal.TEN);
        product.setProductType(ProductType.FOOD);
        product.setName("NomNomPizza");
        product.setReceipt(Collections.singletonList(ProductReceiptLineFixture.getPizza()));
        product.setAvailable(true);

        ProductPreparation preparation = new ProductPreparation();
        preparation.setQueueName("oven_queue");
        preparation.setTimer(40);
        preparation.setInstructions("Put in the oven, THEN eat it.");

        product.setPreparation(preparation);

        return product;
    }
}
