package be.brickbit.lpm.catering.service.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.repository.WalletRepository;
import be.brickbit.lpm.catering.service.wallet.mapper.WalletMapper;
import be.brickbit.lpm.infrastructure.AbstractService;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@Service
public class WalletServiceImpl extends AbstractService<Wallet> implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    @Transactional
    public void addAmount(Long userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId);

        if(wallet == null){
            createNewWallet(userId, amount);
        }else {
            wallet.setAmount(wallet.getAmount().add(amount));
        }
    }

    private void createNewWallet(Long userId, BigDecimal amount) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setAmount(amount);
        walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public void substractAmount(Long userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId);

        if(wallet == null || wallet.getAmount().compareTo(amount) == -1){
            throw new ServiceException("Insufficient funds.");
        }else{
            wallet.setAmount(wallet.getAmount().subtract(amount));
        }
    }

    @Override
    public <T> T findByUserId(Long userId, WalletMapper<T> dtoMapper) {
        return dtoMapper.map(walletRepository.findByUserId(userId));
    }

    @Override
    protected WalletRepository getRepository() {
        return walletRepository;
    }
}