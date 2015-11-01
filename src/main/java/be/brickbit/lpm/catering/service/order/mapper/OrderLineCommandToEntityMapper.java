package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderLineCommandToEntityMapper implements Mapper<OrderLineCommand, OrderLine> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderLine map(OrderLineCommand orderLineCommand) {
        Optional<Product> product = Optional.ofNullable(productRepository.getOne(orderLineCommand.getProductId()));

        if(product.isPresent()) {
            OrderLine orderLine = new OrderLine();

            orderLine.setProduct(product.get());
            orderLine.setQuantity(orderLineCommand.getQuanity());

            return orderLine;
        }else{
            throw new EntityNotFoundException("Invalid Product");
        }
    }
}
