package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.wallet.dto.WalletDto;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;

public class WalletDtoFixture {
    public static WalletDto mutable() {
        return new WalletDto(
                randomDecimal()
        );
    }
}
