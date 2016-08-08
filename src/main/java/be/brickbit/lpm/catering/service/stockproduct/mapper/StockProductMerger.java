package be.brickbit.lpm.catering.service.stockproduct.mapper;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.service.stockproduct.command.EditStockProductCommand;
import be.brickbit.lpm.infrastructure.mapper.Merger;
import org.springframework.stereotype.Component;

@Component
public class StockProductMerger implements Merger<EditStockProductCommand, StockProduct> {
	@Override
	public void merge(EditStockProductCommand editStockProductCommand, StockProduct stockProduct) {
		if (!editStockProductCommand.getName().equals(stockProduct.getName())) {
			stockProduct.setName(editStockProductCommand.getName());
		}

		if (!editStockProductCommand.getClearance().equals(stockProduct.getClearance())) {
			stockProduct.setClearance(editStockProductCommand.getClearance());
		}

		if (!editStockProductCommand.getProductType().equals(stockProduct.getProductType())) {
			stockProduct.setProductType(editStockProductCommand.getProductType());
		}
	}
}
