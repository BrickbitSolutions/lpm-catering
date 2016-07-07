package be.brickbit.lpm.catering.service.stockflow;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.StockFlowRepository;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowCommandToEntityMapper;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowMapper;
import be.brickbit.lpm.catering.service.stockflow.util.StockFlowUtil;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class StockFlowService extends AbstractService<StockFlow> implements IStockFlowService {
    @Autowired
    private StockFlowRepository stockFlowRepository;

    @Autowired
    private StockFlowCommandToEntityMapper stockFlowCommandToEntityMapper;

    @Override
    @Transactional
    public <T> T save(StockFlowCommand command, User someUser, StockFlowMapper<T> dtoMapper){
        StockFlow stockFlow = stockFlowCommandToEntityMapper.map(command);
        stockFlow.setUserId(someUser.getId());
        stockFlowRepository.save(stockFlow);
        return dtoMapper.map(stockFlow);
    }

    @Override
    @Transactional
    public void processStockFlow(Long id) {
        StockFlow stockFlow = Optional.ofNullable(stockFlowRepository.findOne(id)).orElseThrow(this::throwStockFlowNotFoundException);
        for(StockFlowDetail detail : stockFlow.getDetails()){
            StockProduct stockProduct = detail.getStockProduct();

            if(stockProduct.getRemainingConsumptions() == 0){
                stockProduct.setRemainingConsumptions(stockProduct.getMaxConsumptions());
                stockProduct.setStockLevel(StockFlowUtil.calculateNewStock(detail, stockFlow.getStockFlowType()) - 1);
            }else{
                stockProduct.setStockLevel(StockFlowUtil.calculateNewStock(detail, stockFlow.getStockFlowType()));
            }
        }
        stockFlow.setIncluded(true);
        stockFlowRepository.save(stockFlow);
    }

    private EntityNotFoundException throwStockFlowNotFoundException() {
        throw new EntityNotFoundException("Could not find stock flow entry.");
    }

    @Override
    protected StockFlowRepository getRepository() {
        return stockFlowRepository;
    }
}
