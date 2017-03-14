package be.brickbit.lpm.catering.service.stockflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.brickbit.lpm.catering.domain.StockCorrectionLevel;
import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.repository.StockFlowRepository;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowCommandToEntityMapper;
import be.brickbit.lpm.catering.service.stockflow.mapper.StockFlowMapper;
import be.brickbit.lpm.catering.service.stockflow.util.StockFlowUtil;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;
import be.brickbit.lpm.infrastructure.AbstractService;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@Service
public class StockFlowService extends AbstractService<StockFlow> implements IStockFlowService {
    @Autowired
    private StockFlowRepository stockFlowRepository;

    @Autowired
    private StockFlowCommandToEntityMapper stockProductStockFlowCommandToEntityMapper;

    @Override
    @Transactional
    public <T> T save(StockFlowCommand command, Long userId, StockFlowMapper<T> dtoMapper) {
        StockFlow stockFlow = stockProductStockFlowCommandToEntityMapper.map(command);
        stockFlow.setUserId(userId);

        stockFlowRepository.save(stockFlow);

        processStockFlow(stockFlow, command.getLevel());

        return dtoMapper.map(stockFlow);
    }

    private void processStockFlow(StockFlow stockFlow, StockCorrectionLevel level) {
        for (StockFlowDetail detail : stockFlow.getDetails()) {
            switch (level) {
                case STOCK:
                    StockFlowUtil.processStockFlow(detail, stockFlow.getStockFlowType());
                    break;
                case CONSUMPTION:
                    StockFlowUtil.processConsumptionStockFlow(detail, stockFlow.getStockFlowType());
                    break;
                default:
                    throw new ServiceException(String.format("No Stock Flow Process determined " +
                            "for flow type %s", level.toString()));
            }
        }
    }

    @Override
    protected StockFlowRepository getRepository() {
        return stockFlowRepository;
    }
}
