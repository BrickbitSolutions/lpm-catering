package be.brickbit.lpm.catering.service.wallet;

import java.math.BigDecimal;

import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.service.wallet.mapper.WalletMapper;
import be.brickbit.lpm.infrastructure.Service;

public interface WalletService extends Service<Wallet>{
    void addAmount(Long userId, BigDecimal amount);
    void substractAmount(Long userId, BigDecimal amount);
    <T> T findByUserId(Long userId, WalletMapper<T> dtoMapper);
}
