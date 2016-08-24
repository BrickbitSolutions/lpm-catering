package be.brickbit.lpm.catering.service.product.mapper;

import org.junit.Test;

import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.fixture.ProductReceiptLineFixture;
import be.brickbit.lpm.catering.service.product.dto.ReceiptDto;

import static org.assertj.core.api.Assertions.assertThat;

public class ReceiptDtoMapperTest {
	private ReceiptDtoMapper mapper = new ReceiptDtoMapper();

	@Test
	public void map() throws Exception {
		ProductReceiptLine receiptLine = ProductReceiptLineFixture.getJupiler();

		ReceiptDto receiptDto = mapper.map(receiptLine);

        assertThat(receiptDto.getQuantity()).isEqualTo(receiptLine.getQuantity());
        assertThat(receiptDto.getStockProductId()).isEqualTo(receiptLine.getStockProduct().getId());
	}

}