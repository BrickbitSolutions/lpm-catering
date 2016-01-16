package be.brickbit.lpm.catering.service.stockcorrection;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.service.stockcorrection.command.StockCorrectionCommand;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.StockCorrectionMapper;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.Service;

public interface IStockCorrectionService extends Service<StockCorrection>{
    <T> T save(StockCorrectionCommand command, User someUser, StockCorrectionMapper<T> dtoMapper);
}
