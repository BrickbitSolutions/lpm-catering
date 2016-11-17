package be.brickbit.lpm.catering.controller;

import com.mysema.query.jpa.impl.JPAQuery;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.domain.QWallet;
import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.fixture.EditWalletAmountCommandFixture;
import be.brickbit.lpm.catering.fixture.WalletFixture;
import be.brickbit.lpm.catering.service.wallet.command.EditWalletAmountCommand;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WalletControllerIT extends AbstractControllerIT {

    @Test
    public void getsWallet() throws Exception {
        Wallet wallet = WalletFixture.mutable();
        wallet.setUserId(userDetails().getId());

        insert(wallet);

        performGet("/user/wallet")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", equalTo(wallet.getAmount().doubleValue())));
    }

    @Test
    public void getsWalletForUserId() throws Exception {
        Wallet wallet = WalletFixture.mutable();
        wallet.setUserId(userDetails().getId());

        insert(wallet);

        performGet(String.format("/user/wallet?userId=%d", userDetails().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", equalTo(wallet.getAmount().doubleValue())));
    }

    @Test
    public void addsAmountToWallet() throws Exception {
        BigDecimal startCapital = randomDecimal(10.0, 100.0);
        Wallet wallet = WalletFixture.mutable();
        wallet.setUserId(userDetails().getId());
        wallet.setAmount(startCapital);

        insert(wallet);

        EditWalletAmountCommand command = EditWalletAmountCommandFixture.mutable();
        command.setUserId(userDetails().getId());
        command.setAmount(randomDecimal(1.0, 10.0));

        performPost("/user/wallet/add", command)
                .andExpect(status().isNoContent());

        Wallet result = new JPAQuery(getEntityManager())
                .from(QWallet.wallet)
                .where(QWallet.wallet.userId.eq(userDetails().getId()))
                .uniqueResult(QWallet.wallet);

        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(startCapital.add(command.getAmount()));
    }

    @Test
    public void subtractsAmountToWallet() throws Exception {
        BigDecimal startCapital = randomDecimal(10.0, 100.0);
        Wallet wallet = WalletFixture.mutable();
        wallet.setUserId(userDetails().getId());
        wallet.setAmount(startCapital);

        insert(wallet);

        EditWalletAmountCommand command = EditWalletAmountCommandFixture.mutable();
        command.setUserId(userDetails().getId());
        command.setAmount(randomDecimal(1.0, 10.0));

        performPost("/user/wallet/substract", command)
                .andExpect(status().isNoContent());

        Wallet result = new JPAQuery(getEntityManager())
                .from(QWallet.wallet)
                .where(QWallet.wallet.userId.eq(userDetails().getId()))
                .uniqueResult(QWallet.wallet);

        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(startCapital.subtract(command.getAmount()));
    }
}