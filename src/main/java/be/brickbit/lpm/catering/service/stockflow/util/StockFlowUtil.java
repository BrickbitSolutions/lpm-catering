package be.brickbit.lpm.catering.service.stockflow.util;

import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

public class StockFlowUtil {
    public static Integer processStockFlow(StockFlowDetail stockFlowDetail, StockFlowType type){
        switch (type){
            case CORRECTION:
                return deductQuantity(stockFlowDetail.getStockProduct().getStockLevel(), stockFlowDetail.getQuantity());
            case LOSS:
                return deductQuantity(stockFlowDetail.getStockProduct().getStockLevel(), stockFlowDetail.getQuantity());
            case PURCHASED:
                return addQuantity(stockFlowDetail.getStockProduct().getStockLevel(), stockFlowDetail.getQuantity());
            case RETURNED:
                return deductQuantity(stockFlowDetail.getStockProduct().getStockLevel(), stockFlowDetail.getQuantity());
            case SOLD:
                return deductQuantity(stockFlowDetail.getStockProduct().getStockLevel(), stockFlowDetail.getQuantity());
            default:
                    throw new ServiceException("Calculation for Stock flow type '" + type.toString() + "' is not implemented.");
        }
    }

    private static Integer addQuantity(Integer stockLevel, Integer quantity) {
        return stockLevel + quantity;
    }

    private static Integer deductQuantity(Integer stockLevel, Integer quantity) {
        return stockLevel - quantity;
    }

    public static Integer calculateCurrentStockLevel(StockProduct stockProduct) {
        if(stockProduct.getMaxConsumptions() == 1){
            return stockProduct.getStockLevel();
        }else{
            return (stockProduct.getStockLevel() * stockProduct.getMaxConsumptions()) + stockProduct.getRemainingConsumptions();
        }
    }

    public static void calculateNewStockLevel(StockProduct stockProduct, Integer quantityToProcess) {
        if(calculateCurrentStockLevel(stockProduct) < quantityToProcess){
            throw new ServiceException(String.format("Not enough '%s' in stock to process order!", stockProduct.getName()));
        }

        if(stockProduct.getMaxConsumptions() == 1){
            stockProduct.setStockLevel(stockProduct.getStockLevel() - quantityToProcess);
        }else{
            if(stockProduct.getRemainingConsumptions() >= quantityToProcess){
                stockProduct.setRemainingConsumptions(stockProduct.getRemainingConsumptions() - quantityToProcess);
            }else{
                Integer totalStockToSubtract = 1;
                Integer quantityLeft = quantityToProcess - stockProduct.getRemainingConsumptions();
                Integer remainingConsumptions = stockProduct.getMaxConsumptions() - (quantityLeft % stockProduct.getMaxConsumptions());
                totalStockToSubtract += quantityLeft / stockProduct.getMaxConsumptions();

                stockProduct.setStockLevel(stockProduct.getStockLevel() - totalStockToSubtract);
                stockProduct.setRemainingConsumptions(remainingConsumptions);
            }
        }
    }
}
