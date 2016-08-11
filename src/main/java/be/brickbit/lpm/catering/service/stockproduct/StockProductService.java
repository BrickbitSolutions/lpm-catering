package be.brickbit.lpm.catering.service.stockproduct;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockproduct.command.EditStockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductCommandToEntityMapper;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductMapper;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductMerger;
import be.brickbit.lpm.infrastructure.AbstractService;
import be.brickbit.lpm.infrastructure.exception.ServiceException;
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

    @Autowired
    private StockProductMerger stockProductMerger;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public <T> List<T> findAllByTypeAndClearance(ProductType type, ClearanceType clearance, StockProductMapper<T> mapper) {
        return stockProductRepository.findByProductTypeAndClearance(type, clearance).stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public <T> List<T> findAllByType(ProductType type, StockProductMapper<T> mapper){
        return stockProductRepository.findByProductType(type).stream().map(mapper::map).collect(Collectors.toList());
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
    public void updateStockProduct(Long stockProductId, EditStockProductCommand command){
        stockProductMerger.merge(command, stockProductRepository.findOne(stockProductId));
    }

    @Override
    @Transactional
    public void delete(Long id){
        if(productRepository.countByReceiptStockProductId(id) > 0){
            throw new ServiceException("Can not delete, stock product is in use.");
        }

        stockProductRepository.delete(id);
    }

    @Override
    protected StockProductRepository getRepository() {
        return stockProductRepository;
    }
}
