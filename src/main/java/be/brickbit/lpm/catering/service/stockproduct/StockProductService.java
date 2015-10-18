package be.brickbit.lpm.catering.service.stockproduct;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.stockproduct.command.SaveStockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.dto.StockProductDto;
import be.brickbit.lpm.catering.service.stockproduct.mapper.SaveStockProductMapper;
import be.brickbit.lpm.catering.service.stockproduct.mapper.StockProductDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockProductService implements IStockProductService{

    @Autowired
    private StockProductRepository stockProductRepository;

    @Autowired
    private StockProductDtoMapper stockProductDtoMapper;

    @Autowired
    private SaveStockProductMapper saveStockProductMapper;

    @Override
    @Transactional(readOnly = true)
    public List<StockProductDto> findAll() {
        return stockProductRepository.findAll().stream().map(stockProduct -> stockProductDtoMapper.map(stockProduct)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockProductDto> findAllByTypeAndClearance(ProductType type, ClearanceType clearance) {
        return stockProductRepository.findByProductTypeAndClearance(type, clearance).stream().map(stockProduct -> stockProductDtoMapper.map(stockProduct)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveNewStockProduct(SaveStockProductCommand command){
        stockProductRepository.save(saveStockProductMapper.map(command));
    }

    @Override
    @Transactional
    public void deleteStockProduct(Long id){
        stockProductRepository.delete(id);
    }
}
