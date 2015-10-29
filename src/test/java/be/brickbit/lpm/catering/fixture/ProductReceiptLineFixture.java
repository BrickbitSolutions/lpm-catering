package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ProductReceiptLine;

public class ProductReceiptLineFixture {
    public static ProductReceiptLine getReceiptLine1(){
        ProductReceiptLine receiptLine = new ProductReceiptLine();

        receiptLine.setStockProduct(StockProductFixture.getStockProduct());
        receiptLine.setQuantity(2);

        return receiptLine;
    }

    public static ProductReceiptLine getReceiptLine2(){
        ProductReceiptLine receiptLine = new ProductReceiptLine();

        receiptLine.setStockProduct(StockProductFixture.getStockProductDuvel());
        receiptLine.setQuantity(2);

        return receiptLine;
    }
}
