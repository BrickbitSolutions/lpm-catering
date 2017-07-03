package be.brickbit.lpm.catering.service.wallet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.fixture.WalletDtoFixture;
import be.brickbit.lpm.catering.fixture.WalletFixture;
import be.brickbit.lpm.catering.repository.WalletRepository;
import be.brickbit.lpm.catering.controller.dto.WalletDto;
import be.brickbit.lpm.catering.controller.mapper.WalletDtoMapper;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WalletServiceImplTest {
    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletDtoMapper mapper;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void addsAmountToWallet() throws Exception {
        BigDecimal amount = randomDecimal(0.0, 100.0);
        BigDecimal originalAmount = randomDecimal(0.0, 100.0);
        Long userId = randomLong();
        Wallet wallet = WalletFixture.mutable();
        wallet.setAmount(originalAmount);

        when(walletRepository.findByUserId(userId)).thenReturn(wallet);

        walletService.addAmount(userId, amount);

        assertThat(wallet.getAmount()).isEqualTo(originalAmount.add(amount));
    }

    @Test
    public void createsNewWallet() throws Exception {

        walletService.addAmount(randomLong(), randomDecimal(0.0, 100.0));

        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    public void substractsAmount() throws Exception {
        BigDecimal amount = randomDecimal(0.0, 100.0);
        BigDecimal originalAmount = randomDecimal(100.0, 200.0);
        Long userId = randomLong();
        Wallet wallet = WalletFixture.mutable();
        wallet.setAmount(originalAmount);

        when(walletRepository.findByUserId(userId)).thenReturn(wallet);

        walletService.substractAmount(userId, amount);

        assertThat(wallet.getAmount()).isEqualTo(originalAmount.subtract(amount));
    }

    @Test
    public void throwsServiceExceptionWithInsufficientFunds() throws Exception {
        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("Insufficient funds.");

        BigDecimal amount = randomDecimal(100.0, 200.0);
        BigDecimal originalAmount = randomDecimal(0.0, 100.0);
        Long userId = randomLong();
        Wallet wallet = WalletFixture.mutable();
        wallet.setAmount(originalAmount);

        when(walletRepository.findByUserId(userId)).thenReturn(wallet);

        walletService.substractAmount(userId, amount);
    }

    @Test
    public void findsWalletByUserId() throws Exception {
        Wallet wallet = WalletFixture.mutable();
        Long userId = randomLong();

        WalletDto walletDto = WalletDtoFixture.mutable();

        when(walletRepository.findByUserId(userId)).thenReturn(wallet);
        when(mapper.map(wallet)).thenReturn(walletDto);

        WalletDto result = walletService.findByUserId(userId, mapper);

        assertThat(result).isSameAs(walletDto);
    }
}