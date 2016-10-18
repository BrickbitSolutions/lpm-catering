package be.brickbit.lpm.catering.service.wallet.command;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EditWalletAmountCommand {
    @NotNull
    private Long userId;
    @NotNull
    private BigDecimal amount;
}
