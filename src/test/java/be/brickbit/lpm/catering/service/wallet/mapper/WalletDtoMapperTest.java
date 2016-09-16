package be.brickbit.lpm.catering.service.wallet.mapper;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.fixture.WalletFixture;
import be.brickbit.lpm.catering.service.wallet.dto.WalletDto;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletDtoMapperTest {
    private WalletDtoMapper walletDtoMapper;

    @Before
    public void setUp() throws Exception {
        walletDtoMapper = new WalletDtoMapper();
    }

    @Test
    public void mapWalletDto() throws Exception {
        Wallet wallet = WalletFixture.mutable();

        WalletDto result = walletDtoMapper.map(wallet);

        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(wallet.getAmount());
    }

    @Test
    public void returnsZeroAmountWallet() throws Exception {
        WalletDto result = walletDtoMapper.map(null);

        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(BigDecimal.ZERO);
    }
}