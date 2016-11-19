package be.brickbit.lpm.catering.service.wallet.dto;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class WalletDto {
    private BigDecimal amount;
}
