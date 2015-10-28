package be.brickbit.lpm.catering.service.stockproduct;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductMapper;
import be.brickbit.lpm.infrastructure.Service;

import java.util.List;

public interface IStockProductService extends Service<StockProduct>{
    <T> List<T> findAllByTypeAndClearance(ProductType type, ClearanceType clearance, StockProductMapper<T> mapper);
    <T> T save(StockProductCommand command, StockProductMapper<T> dtoMapper);
    void delete(Long id);
}