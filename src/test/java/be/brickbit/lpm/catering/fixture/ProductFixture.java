package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductPreparation;
import be.brickbit.lpm.catering.domain.ProductType;

import java.math.BigDecimal;
import java.util.Collections;

public class ProductFixture {
    public static Product getProduct(){
        Product product = new Product();

        product.setClearance(ClearanceType.PLUS_16);
        product.setPrice(BigDecimal.ONE);
        product.setProductType(ProductType.DRINKS);
        product.setName("Jupiler 33cl");
        product.setReceipt(Collections.singletonList(ProductReceiptLineFixture.getReceiptLine1()));

        return product;
    }

    public static Product getProductPizza(){
        Product product = new Product();

        product.setClearance(ClearanceType.ANY);
        product.setPrice(BigDecimal.TEN);
        product.setProductType(ProductType.FOOD);
        product.setName("Pizza Margarita");
        product.setReceipt(Collections.singletonList(ProductReceiptLineFixture.getPizza()));

        ProductPreparation preparation = new ProductPreparation();
        preparation.setId(1L);
        preparation.setQueueName("oven_queue");
        preparation.setTimer(40);
        preparation.setInstructions("Make sure oven is heated to 180 degrees.");

        product.setPreparation(preparation);

        return product;
    }
}
