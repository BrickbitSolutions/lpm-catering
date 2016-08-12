package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.ProductReceiptLine;

public class ProductReceiptLineFixture {
    public static ProductReceiptLine getCola(){
        ProductReceiptLine receiptLine = new ProductReceiptLine();

        receiptLine.setStockProduct(StockProductFixture.getStockProductJupiler());
        receiptLine.setQuantity(1);

        return receiptLine;
    }

    public static ProductReceiptLine getJupiler(){
        ProductReceiptLine receiptLine = new ProductReceiptLine();

        receiptLine.setStockProduct(StockProductFixture.getStockProductJupiler());
        receiptLine.setQuantity(1);

        return receiptLine;
    }

    public static ProductReceiptLine getPizza(){
        ProductReceiptLine receiptLine = new ProductReceiptLine();

        receiptLine.setStockProduct(StockProductFixture.getStockProductPizza());
        receiptLine.setQuantity(3);

        return receiptLine;
    }

    public static ProductReceiptLine getLasagna(){
        ProductReceiptLine receiptLine = new ProductReceiptLine();

        receiptLine.setStockProduct(StockProductFixture.getStockProductLasagna());
        receiptLine.setQuantity(1);

        return receiptLine;
    }
}
