package be.brickbit.lpm.catering.controller.mapper;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.controller.dto.SupplementDto;
import be.brickbit.lpm.catering.service.product.mapper.ProductMapper;
import be.brickbit.lpm.catering.service.stockflow.util.StockFlowUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.controller.dto.ProductDto;

@Component
public class ProductDtoMapper implements ProductMapper<ProductDto> {

	@Autowired
	private SupplementDtoMapper supplementDtoMapper;

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
                someProduct.getAvailable(),
				someProduct.getReservationOnly(),
				mapSupplements(someProduct)
		);
	}

	private List<SupplementDto> mapSupplements(Product someProduct) {
		if(CollectionUtils.isEmpty(someProduct.getSupplements())){
			return Lists.newArrayList();
		}

		return someProduct.getSupplements().stream().map(supplementDtoMapper::map).collect(Collectors.toList());
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
