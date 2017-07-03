package be.brickbit.lpm.catering.controller.mapper;

import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.controller.dto.ReceiptDto;
import be.brickbit.lpm.catering.service.product.mapper.ReceiptMapper;

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
