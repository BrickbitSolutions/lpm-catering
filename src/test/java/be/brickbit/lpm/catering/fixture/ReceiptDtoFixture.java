package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.product.dto.ReceiptDto;

public class ReceiptDtoFixture {
    public static ReceiptDto getReceiptLine1Dto(){
        return new ReceiptDto(
                1L,
                2
        );
    }
}
