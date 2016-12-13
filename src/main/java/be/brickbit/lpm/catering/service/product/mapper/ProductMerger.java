package be.brickbit.lpm.catering.service.product.mapper;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.product.command.EditProductCommand;
import be.brickbit.lpm.infrastructure.mapper.Merger;

@Component
public class ProductMerger implements Merger<EditProductCommand, Product> {
    @Autowired
    private StockProductRepository stockProductRepository;

    @Override
    public void merge(EditProductCommand editProductCommand, Product product) {
        if (!product.getName().equals(editProductCommand.getName())) {
            product.setName(editProductCommand.getName());
        }

        if (!product.getPrice().equals(editProductCommand.getPrice())) {
            product.setPrice(editProductCommand.getPrice());
        }

        if (!product.getReservationOnly().equals(editProductCommand.getReservationOnly())) {
            product.setReservationOnly(editProductCommand.getReservationOnly());
        }

        if (CollectionUtils.isEmpty(editProductCommand.getSupplements())) {
            product.setSupplements(Lists.newArrayList());
        } else {
            product.setSupplements(
                    editProductCommand.getSupplements().stream()
                            .map(id -> stockProductRepository.findOne(id))
                            .collect(Collectors.toList())
            );
        }
    }
}
