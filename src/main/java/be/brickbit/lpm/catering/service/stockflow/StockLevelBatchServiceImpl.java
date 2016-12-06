package be.brickbit.lpm.catering.service.stockflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;

@Component
public class StockLevelBatchServiceImpl implements StockLevelBatchService {
    private final Logger log = LoggerFactory.getLogger(StockLevelBatchService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    @Scheduled(cron = "0 0 0/1 1/1 * *") // run every hour
    @Transactional
    public void calculateAvgConsumption() {
        log.info("Calculating AVG Consumptions");

        LocalDateTime currentTime = LocalDateTime.now().withSecond(0).withMinute(0);
        List<Order> orders = orderRepository.findAllByTimestampBetween(currentTime, currentTime.minusHours(1L));
        calculateAvgConsumptionProducts(orders);
        calculateAvgConsumptionStockProducts(orders);

        log.info("Calculating AVG Consumptions");
    }

    private void calculateAvgConsumptionStockProducts(List<Order> orders) {
        Map<Product, Map<StockProduct, Integer>> stockProductUsagePerProduct = getStockProductUsagePerProduct(orders);
        Map<StockProduct, Integer> stockProductUsage = getStockProductUsage(stockProductUsagePerProduct);

        stockProductUsage.keySet().forEach(stockProduct -> {
            Integer avgConsumption = new Double(Math.ceil(stockProductUsage.get(stockProduct) / orders.size())).intValue();
            stockProduct.setAvgConsumption(avgConsumption);
            stockProductRepository.save(stockProduct);
            log.debug("Updating StockProduct #%d with a AVG Conumption of %d", stockProduct.getId(), avgConsumption);
        });
    }

    private Map<StockProduct, Integer> getStockProductUsage(Map<Product, Map<StockProduct, Integer>> stockProductUsagePerProduct) {
        return stockProductUsagePerProduct.values().stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)));
    }

    private Map<Product, Map<StockProduct, Integer>> getStockProductUsagePerProduct(List<Order> orders) {
        Map<Product, Integer> productUsage = collectProductUsage(orders);

        return productUsage.keySet().stream().collect(
                Collectors.toMap(
                        Function.identity(),
                        product -> product.getReceipt().stream().collect(
                                Collectors.groupingBy(
                                        ProductReceiptLine::getStockProduct, Collectors.summingInt(line -> {
                                            Integer productUsageQuantity = productUsage.get(product);
                                            if (productUsageQuantity != null && productUsageQuantity > 0) {
                                                return line.getQuantity() * productUsageQuantity;
                                            } else {
                                                return line.getQuantity();
                                            }
                                        })))));
    }

    private void calculateAvgConsumptionProducts(List<Order> orders) {
        Map<Product, Integer> productUsage = collectProductUsage(orders);

        productUsage.keySet().forEach(product -> {
            Integer avgConsumption = new Double(Math.ceil(productUsage.get(product) / orders.size())).intValue();
            product.setAvgConsumption(avgConsumption);
            productRepository.save(product);
            log.debug("Updating Product #%d with a AVG Conumption of %d", product.getId(), avgConsumption);
        });
    }

    private Map<Product, Integer> collectProductUsage(List<Order> orders) {
        return collectOrderLines(orders)
                .stream()
                .collect(Collectors.groupingBy(OrderLine::getProduct, Collectors.summingInt(OrderLine::getQuantity)));
    }

    private List<OrderLine> collectOrderLines(List<Order> orders) {
        return orders.stream().flatMap(order -> order.getOrderLines().stream()).collect(Collectors.toList());
    }
}
