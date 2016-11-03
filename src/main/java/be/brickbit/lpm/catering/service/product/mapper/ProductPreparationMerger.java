package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductPreparation;
import be.brickbit.lpm.catering.service.product.command.EditProductPreparationCommand;
import be.brickbit.lpm.infrastructure.mapper.Merger;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductPreparationMerger implements Merger<EditProductPreparationCommand, Product> {
    @Override
    public void merge(EditProductPreparationCommand editProductPreparationCommand, Product product) {
        ProductPreparation preparation = product.getPreparation();

        Integer timerInSeconds = editProductPreparationCommand.getTimerInMinutes() != null ?
                editProductPreparationCommand.getTimerInMinutes() * 60 : null;

        if(preparation == null){
            preparation = new ProductPreparation();
            product.setPreparation(preparation);
        }

        if (!Objects.equals(preparation.getQueueName(), editProductPreparationCommand.getQueueName())) {
            preparation.setQueueName(editProductPreparationCommand.getQueueName());
        }

        if (!Objects.equals(preparation.getTimer(), timerInSeconds)) {
            preparation.setTimer(timerInSeconds);
        }

        if (!Objects.equals(preparation.getInstructions(), editProductPreparationCommand.getInstructions())) {
            preparation.setInstructions(editProductPreparationCommand.getInstructions());
        }
    }
}
