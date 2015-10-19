package be.brickbit.lpm.catering.service.stockcorrection.mapper;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.service.AbstractMapper;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockCorrectionDtoMapper extends AbstractMapper<StockCorrection, StockCorrectionDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public StockCorrectionDto map(StockCorrection someStockCorrection) {
        User user = userRepository.findOne(someStockCorrection.getUserId());

        return new StockCorrectionDto(
                someStockCorrection.getStockProduct().getName(),
                someStockCorrection.getQuantity(),
                someStockCorrection.getTimestamp(),
                user.getUsername()
        );
    }
}
