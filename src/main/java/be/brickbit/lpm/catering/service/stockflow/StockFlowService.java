package be.brickbit.lpm.catering.service.stockflow;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowDetail;
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
        StockFlow stockFlow = stockFlowRepository.findOne(id);
        for(StockFlowDetail detail : stockFlow.getDetails()){
            detail.getStockProduct().setStockLevel(StockFlowUtil.calculateNewStock(detail, stockFlow.getStockFlowType()));
        }
        stockFlow.setIncluded(true);
        stockFlowRepository.save(stockFlow);
    }

    @Override
    protected StockFlowRepository getRepository() {
        return stockFlowRepository;
    }
}
