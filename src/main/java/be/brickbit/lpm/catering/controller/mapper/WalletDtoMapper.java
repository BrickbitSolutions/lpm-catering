package be.brickbit.lpm.catering.controller.mapper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.controller.dto.WalletDto;
import be.brickbit.lpm.catering.service.wallet.mapper.WalletMapper;

@Component
public class WalletDtoMapper implements WalletMapper<WalletDto> {
    @Override
    public WalletDto map(Wallet wallet) {
        if(wallet == null){
            return new WalletDto(BigDecimal.ZERO);
        }

        return new WalletDto(wallet.getAmount());
    }
}
