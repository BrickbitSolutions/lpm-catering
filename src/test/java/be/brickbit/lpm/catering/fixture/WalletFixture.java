package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.Wallet;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;

public class WalletFixture {

    public static Wallet mutable() {
        Wallet wallet = new Wallet();

        wallet.setUserId(randomLong());
        wallet.setAmount(randomDecimal(10.0, 1000.0));

        return wallet;
    }
}
