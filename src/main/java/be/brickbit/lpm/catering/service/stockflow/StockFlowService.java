package be.brickbit.lpm.catering.service.stockflow;

import be.brickbit.lpm.catering.service.stockflow.command.StockCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class StockFlowService extends AbstractService<StockFlow> implements IStockFlowService {
	@Autowired
	private StockFlowRepository stockFlowRepository;

	@Autowired
	private StockFlowCommandToEntityMapper stockProductStockFlowCommandToEntityMapper;

	@Override
	@Transactional
	public <T> T save(StockFlowCommand command, User someUser, StockFlowMapper<T> dtoMapper) {
		StockFlow stockFlow = stockProductStockFlowCommandToEntityMapper.map(command);
		stockFlow.setUserId(someUser.getId());

		stockFlowRepository.save(stockFlow);

		processStockFlow(stockFlow, command.getLevel());

		return dtoMapper.map(stockFlow);
	}

	private void processStockFlow(StockFlow stockFlow, StockCorrectionLevel level) {
		for (StockFlowDetail detail : stockFlow.getDetails()) {
			switch (level){
				case STOCK:
					StockFlowUtil.processStockFlow(detail, stockFlow.getStockFlowType());
					break;
				case CONSUMPTION:
					StockFlowUtil.processConsumptionStockFlow(detail, stockFlow.getStockFlowType());
					break;
			}
		}
	}

	@Override
	protected StockFlowRepository getRepository() {
		return stockFlowRepository;
	}
}
