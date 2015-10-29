package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
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
}
