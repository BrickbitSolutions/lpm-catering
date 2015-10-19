package be.brickbit.lpm.catering.service.stockcorrection;

import be.brickbit.lpm.catering.service.stockcorrection.command.NewStockCorrectionCommand;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.core.domain.User;

import java.util.List;

public interface IStockCorrectionService {
    public void saveStockCorrection(NewStockCorrectionCommand command, User someUser);
    List<StockCorrectionDto> findAll();
}
