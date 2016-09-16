package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.wallet.command.EditWalletAmountCommand;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;

public class EditWalletAmountCommandFixture {
    public static EditWalletAmountCommand mutable() {
        return new EditWalletAmountCommand(
                randomDecimal(1.0, 10.0)
        );
    }
}
