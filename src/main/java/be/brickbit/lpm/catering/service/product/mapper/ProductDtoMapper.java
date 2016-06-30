package be.brickbit.lpm.catering.service.product.mapper;

import java.util.List;
import java.util.OptionalInt;

import org.springframework.stereotype.Component;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.service.product.dto.ProductDto;

@Component
public class ProductDtoMapper implements ProductMapper<ProductDto> {

	@Override
	public ProductDto map(Product someProduct) {
		return new ProductDto(
				someProduct.getId(),
				someProduct.getName(),
				someProduct.getPrice(),
				someProduct.getProductType(),
				someProduct.getClearance(),
				1,
				getStockLevel(someProduct.getReceipt()));
	}

	private Integer getStockLevel(List<ProductReceiptLine> receipt) {
		OptionalInt result = receipt.stream().mapToInt(receiptLine -> receiptLine.getStockProduct().getStockLevel() / receiptLine.getQuantity()).min();
		if (result.isPresent()) {
			return result.getAsInt();
		} else {
			return 0;
		}
	}
}
