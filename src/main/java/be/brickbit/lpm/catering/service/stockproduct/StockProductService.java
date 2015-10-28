package be.brickbit.lpm.catering.service.stockproduct;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductCommandToEntityMapper;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductMapper;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockProductService extends AbstractService<StockProduct> implements IStockProductService{

    @Autowired
    private StockProductRepository stockProductRepository;

    @Autowired
    private StockProductCommandToEntityMapper stockProductCommandToEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public <T> List<T> findAllByTypeAndClearance(ProductType type, ClearanceType clearance, StockProductMapper<T> mapper) {
        return stockProductRepository.findByProductTypeAndClearance(type, clearance).stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public <T> T save(StockProductCommand command, StockProductMapper<T> dtoMapper) {
        StockProduct stockProduct = stockProductCommandToEntityMapper.map(command);
        stockProductRepository.save(stockProduct);
        return dtoMapper.map(stockProduct);
    }

    @Override
    @Transactional
    public void delete(Long id){
        stockProductRepository.delete(id);
    }

    @Override
    protected StockProductRepository getRepository() {
        return stockProductRepository;
    }
}
