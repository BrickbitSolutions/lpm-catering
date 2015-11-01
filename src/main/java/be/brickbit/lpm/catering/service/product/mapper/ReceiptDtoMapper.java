package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.service.product.dto.ReceiptDto;
import org.springframework.stereotype.Component;

@Component
public class ReceiptDtoMapper implements ReceiptMapper<ReceiptDto> {

    @Override
    public ReceiptDto map(ProductReceiptLine someProductReceiptLine) {
        return new ReceiptDto(
                someProductReceiptLine.getStockProduct().getId(),
                someProductReceiptLine.getQuantity()
        );
    }
}
