package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.service.product.command.EditProductCommand;
import be.brickbit.lpm.infrastructure.mapper.Merger;
import org.springframework.stereotype.Component;

@Component
public class ProductMerger implements Merger<EditProductCommand, Product> {
    @Override
    public void merge(EditProductCommand editProductCommand, Product product) {
        if(!product.getName().equals(editProductCommand.getName())){
            product.setName(editProductCommand.getName());
        }

        if(!product.getPrice().equals(editProductCommand.getPrice())){
            product.setPrice(editProductCommand.getPrice());
        }

        if(!product.getReservationOnly().equals(editProductCommand.getReservationOnly())){
            product.setReservationOnly(editProductCommand.getReservationOnly());
        }
    }
}
