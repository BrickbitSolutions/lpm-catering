package be.brickbit.lpm.catering.service.stockflow.util;

import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockFlowType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

public class StockFlowUtil {
    public static void processStockFlow(StockFlowDetail stockFlowDetail, StockFlowType type){
        switch (type){
            case CORRECTION:
            case LOSS:
            case RETURNED:
            case SOLD:
                deductStock(stockFlowDetail.getStockProduct(), stockFlowDetail.getQuantity());
                break;
            case PURCHASED:
                addStock(stockFlowDetail.getStockProduct(), stockFlowDetail.getQuantity());
                break;
            default:
                throw new ServiceException("Calculation for Stock flow type '" + type.toString() + "' is not implemented.");
        }
    }

    public static void processConsumptionStockFlow(StockFlowDetail stockFlowDetail, StockFlowType stockFlowType) {
        switch (stockFlowType){
            case CORRECTION:
            case LOSS:
            case RETURNED:
            case SOLD:
                calculateNewStockLevel(stockFlowDetail.getStockProduct(), stockFlowDetail.getQuantity() * -1);
                break;
            case PURCHASED:
                calculateNewStockLevel(stockFlowDetail.getStockProduct(), stockFlowDetail.getQuantity());
                break;
            default:
                throw new ServiceException("Calculation for Stock flow type '" + stockFlowType.toString() + "' is not implemented.");
        }
    }

    private static void addStock(StockProduct stockProduct, Integer quantity) {
        Integer quantityToAdd = quantity;

        if(stockProduct.getRemainingConsumptions() == 0){
            stockProduct.setRemainingConsumptions(stockProduct.getMaxConsumptions());
            quantityToAdd--;
        }

        stockProduct.setStockLevel(stockProduct.getStockLevel() + quantityToAdd);
    }

    private static void deductStock(StockProduct stockProduct, Integer quantity) {
        if(quantity > stockProduct.getStockLevel()){
            throw new ServiceException("Stock or Consumption level cannot fall below zero");
        }

        stockProduct.setStockLevel(stockProduct.getStockLevel() - quantity);
    }

    public static Integer calculateCurrentStockLevel(StockProduct stockProduct) {
        if(stockProduct.getMaxConsumptions() == 1){
            return stockProduct.getStockLevel();
        }else{
            return (stockProduct.getStockLevel() * stockProduct.getMaxConsumptions()) + stockProduct.getRemainingConsumptions();
        }
    }


    private static void deductStockConsumptions(StockProduct stockProduct, Integer quantityToProcess){
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

    private static void addStockConsumptions(StockProduct stockProduct, Integer quantityToProcess){
        Integer quantityWithRemainingConsumptions = quantityToProcess + stockProduct.getRemainingConsumptions();

        if(quantityWithRemainingConsumptions > stockProduct.getMaxConsumptions()){
            Integer consumptionsToProcess = quantityWithRemainingConsumptions % stockProduct.getMaxConsumptions();
            Integer completeStockToProcess = quantityWithRemainingConsumptions / stockProduct.getMaxConsumptions();

            stockProduct.setStockLevel(stockProduct.getStockLevel() + completeStockToProcess);
            stockProduct.setRemainingConsumptions(consumptionsToProcess);
        }else{
            stockProduct.setRemainingConsumptions(quantityWithRemainingConsumptions);
        }
    }

    public static void calculateNewStockLevel(StockProduct stockProduct, Integer quantityToProcess) {
        if(quantityToProcess < 0){
            deductStockConsumptions(stockProduct, Math.abs(quantityToProcess));
        }else{
            if(stockProduct.getMaxConsumptions() == 1){
                addStock(stockProduct, quantityToProcess);
            }else {
                addStockConsumptions(stockProduct, quantityToProcess);
            }
        }
    }
}
