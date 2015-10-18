package be.brickbit.lpm.catering.service.stockproduct;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.service.stockproduct.command.SaveStockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.dto.StockProductDto;

import java.util.List;

public interface IStockProductService {
    List<StockProductDto> findAll();
    List<StockProductDto> findAllByTypeAndClearance(ProductType type, ClearanceType clearance);
    void saveNewStockProduct(SaveStockProductCommand command);
    void deleteStockProduct(Long id);
}