package be.brickbit.lpm.catering.service.wallet.mapper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.service.wallet.dto.WalletDto;

@Component
public class WalletDtoMapper implements WalletMapper<WalletDto>{
    @Override
    public WalletDto map(Wallet wallet) {
        if(wallet == null){
            return new WalletDto(BigDecimal.ZERO);
        }

        return new WalletDto(wallet.getAmount());
    }
}
