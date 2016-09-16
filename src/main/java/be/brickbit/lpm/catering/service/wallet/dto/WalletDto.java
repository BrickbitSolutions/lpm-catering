package be.brickbit.lpm.catering.service.wallet.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WalletDto {
    BigDecimal amount;
}
