package be.brickbit.lpm.catering.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.brickbit.lpm.catering.AbstractRepoIT;
import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.fixture.WalletFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletRepositoryTest extends AbstractRepoIT{

    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void findsByUserId() throws Exception {
        Wallet wallet = WalletFixture.mutable();

        insert(wallet);

        Wallet result = walletRepository.findByUserId(wallet.getUserId());

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(wallet.getUserId());
        assertThat(result.getAmount()).isEqualTo(wallet.getAmount());
    }
}