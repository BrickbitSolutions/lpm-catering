package be.brickbit.lpm.catering.service.stockcorrection;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.repository.StockCorrectionRepository;
import be.brickbit.lpm.catering.service.stockcorrection.command.StockCorrectionCommand;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.StockCorrectionCommandToEntityMapper;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.StockCorrectionDtoMapper;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.StockCorrectionMapper;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockCorrectionService extends AbstractService<StockCorrection> implements IStockCorrectionService {

    @Autowired
    private StockCorrectionRepository stockCorrectionRepository;

    @Autowired
    private StockCorrectionCommandToEntityMapper stockCorrectionCommandToEntityMapper;

    @Override
    @Transactional
    public <T> T save(StockCorrectionCommand command, User someUser, StockCorrectionMapper<T> dtoMapper) {
        StockCorrection correction = stockCorrectionCommandToEntityMapper.map(command);
        correction.setUserId(someUser.getId());
        stockCorrectionRepository.save(correction);
        return dtoMapper.map(correction);
    }

    @Override
    protected StockCorrectionRepository getRepository() {
        return stockCorrectionRepository;
    }
}
