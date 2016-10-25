package be.brickbit.lpm.catering.service.order.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;
import be.brickbit.lpm.infrastructure.exception.ServiceException;
import be.brickbit.lpm.infrastructure.mapper.Mapper;

@Component
public class OrderLineCommandToEntityMapper implements Mapper<OrderLineCommand, OrderLine> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    public OrderLine map(OrderLineCommand orderLineCommand) {
        Optional<Product> product = Optional.ofNullable(productRepository.getOne(orderLineCommand.getProductId()));

        if (product.isPresent()) {
            OrderLine orderLine = new OrderLine();

            orderLine.setProduct(product.get());
            orderLine.setQuantity(orderLineCommand.getQuantity());
            if (CollectionUtils.isNotEmpty(orderLineCommand.getSupplements())) {
                orderLine.setSupplements(
                        orderLineCommand.getSupplements().stream()
                                .map(stockProductId -> findSupplement(stockProductId, product.get()))
                                .collect(Collectors.toList()));
            }
            orderLine.setStatus(OrderStatus.CREATED);

            return orderLine;
        } else {
            throw new ServiceException("Invalid Product");
        }
    }

    private StockProduct findSupplement(Long stockProductId, Product product) {
        StockProduct supplement = Optional.ofNullable(stockProductRepository.findOne(stockProductId))
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Supplement #%d not found", stockProductId)));

        if (product.getSupplements().contains(supplement)) {
            return supplement;
        } else {
            throw new ServiceException(String.format(
                    "'%s' is not a valid supplement for '%s'",
                    supplement.getName(),
                    product.getName())
            );
        }
    }
}
