package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.product.command.ReceiptLineCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReceiptCommandToEntityMapper implements Mapper<ReceiptLineCommand,ProductReceiptLine> {

    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    public ProductReceiptLine map(ReceiptLineCommand someReceiptLineCommand) {

        Optional<StockProduct> stockProduct = Optional.ofNullable(stockProductRepository.findOne(someReceiptLineCommand.getStockProductId()));

        if(stockProduct.isPresent()){
            ProductReceiptLine receiptLine = new ProductReceiptLine();

            receiptLine.setQuantity(someReceiptLineCommand.getQuantity());
            receiptLine.setStockProduct(stockProduct.get());

            return receiptLine;
        }else{
            throw new EntityNotFoundException("Invalid stock product.");
        }
    }
}
