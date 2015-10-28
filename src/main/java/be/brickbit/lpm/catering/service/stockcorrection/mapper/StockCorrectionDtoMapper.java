package be.brickbit.lpm.catering.service.stockcorrection.mapper;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.service.user.dto.UserDto;
import be.brickbit.lpm.catering.service.user.mapper.UserDtoMapper;
import be.brickbit.lpm.core.service.IUserService;
import be.brickbit.lpm.core.service.impl.UserService;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockCorrectionDtoMapper implements StockCorrectionMapper<StockCorrectionDto> {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Override
    public StockCorrectionDto map(StockCorrection someStockCorrection) {
        UserDto user = userService.findOne(someStockCorrection.getUserId(), userDtoMapper);

        return new StockCorrectionDto(
                someStockCorrection.getStockProduct().getName(),
                someStockCorrection.getQuantity(),
                someStockCorrection.getTimestamp(),
                user.getUsername()
        );
    }
}
