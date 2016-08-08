package be.brickbit.lpm.catering.service.stockproduct;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.service.stockproduct.command.EditStockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductMapper;
import be.brickbit.lpm.infrastructure.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IStockProductService extends Service<StockProduct>{
    <T> List<T> findAllByTypeAndClearance(ProductType type, ClearanceType clearance, StockProductMapper<T> mapper);
    <T> List<T> findAllByType(ProductType type, StockProductMapper<T> mapper);
    <T> T save(StockProductCommand command, StockProductMapper<T> dtoMapper);
    void updateStockProduct(Long stockProductId, EditStockProductCommand command);
    void delete(Long id);
}