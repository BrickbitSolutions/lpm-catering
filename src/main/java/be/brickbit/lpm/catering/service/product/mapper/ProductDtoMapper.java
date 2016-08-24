package be.brickbit.lpm.catering.service.product.mapper;

import java.util.List;
import java.util.OptionalInt;

import be.brickbit.lpm.catering.service.stockflow.util.StockFlowUtil;
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
				someProduct.getAvgConsumption(),
				getStockLevel(someProduct.getReceipt()),
                someProduct.getAvailable());
	}

	private Integer getStockLevel(List<ProductReceiptLine> receipt) {
		OptionalInt result = receipt.stream().mapToInt(receiptLine -> StockFlowUtil.calculateCurrentStockLevel(receiptLine.getStockProduct()) / receiptLine.getQuantity()).min();
		if (result.isPresent()) {
			return result.getAsInt();
		} else {
			return 0;
		}
	}
}
