package be.brickbit.lpm.catering.service.stockflow.util;

import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockFlowType;

public class StockFlowUtil {
    public static Integer calculateNewStock(StockFlowDetail stockFlowDetail, StockFlowType type){
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
                    throw new RuntimeException("Calculation for Stock flow type '" + type.toString() + "' is not implemented.");
        }
    }

    private static Integer addQuantity(Integer stockLevel, Integer quantity) {
        return stockLevel + quantity;
    }

    private static Integer deductQuantity(Integer stockLevel, Integer quantity) {
        return stockLevel - quantity;
    }
}
