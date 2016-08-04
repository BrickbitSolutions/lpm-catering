package be.brickbit.lpm.catering.service.stockflow.mapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.infrastructure.exception.ServiceException;
import be.brickbit.lpm.infrastructure.mapper.Mapper;

@Component
public class StockFlowCommandToEntityMapper implements Mapper<StockFlowCommand, StockFlow> {

	@Autowired
	private StockProductRepository stockProductRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public StockFlow map(StockFlowCommand someStockFlowCommand) {
		StockFlow stockFlow = new StockFlow();

		stockFlow.setStockFlowType(someStockFlowCommand.getStockFlowType());
		stockFlow.setTimestamp(LocalDateTime.now());
		stockFlow.setDetails(createStockProductStockFlowDetail(someStockFlowCommand));

		return stockFlow;
	}

	private List<StockFlowDetail> createStockProductStockFlowDetail(StockFlowCommand stockFlowCommand) {
		Optional<StockProduct> stockProduct = Optional.ofNullable(stockProductRepository.findOne(stockFlowCommand.getProductId()));

		if (stockProduct.isPresent()) {
			StockFlowDetail detail = new StockFlowDetail();

			detail.setQuantity(stockFlowCommand.getQuantity());
			detail.setStockProduct(stockProduct.get());
			return Arrays.asList(detail);
		}

		throw new ServiceException("Stock Product not found.");
	}

	private List<StockFlowDetail> createProductStockFlowDetail(StockFlowCommand stockFlowCommand) {
		Optional<Product> product = Optional.ofNullable(productRepository.findOne(stockFlowCommand.getProductId()));

		if (product.isPresent()) {
			return product.get().getReceipt().stream().map(productReceiptLine -> {
				StockFlowDetail detail = new StockFlowDetail();
				detail.setStockProduct(productReceiptLine.getStockProduct());
				detail.setQuantity(productReceiptLine.getQuantity() * stockFlowCommand.getQuantity());
				return detail;
			}).collect(Collectors.toList());
		} else {
			throw new ServiceException("Product not found.");
		}
	}
}
