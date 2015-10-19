package be.brickbit.lpm.catering.service.stockcorrection;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.repository.StockCorrectionRepository;
import be.brickbit.lpm.catering.service.stockcorrection.command.NewStockCorrectionCommand;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.NewStockCorrectionMapper;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.StockCorrectionDtoMapper;
import be.brickbit.lpm.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockCorrectionService implements IStockCorrectionService {

    @Autowired
    private StockCorrectionRepository stockCorrectionRepository;

    @Autowired
    private StockCorrectionDtoMapper stockCorrectionDtoMapper;

    @Autowired
    private NewStockCorrectionMapper newStockCorrectionMapper;

    @Override
    public void saveStockCorrection(NewStockCorrectionCommand command, User someUser) {
        StockCorrection correction = newStockCorrectionMapper.map(command);
        correction.setUserId(someUser.getId());
        stockCorrectionRepository.save(correction);
    }

    @Override
    public List<StockCorrectionDto> findAll() {
        return stockCorrectionRepository.findAll().stream().map(stockCorrectionDtoMapper::map).collect(Collectors.toList());
    }
}
