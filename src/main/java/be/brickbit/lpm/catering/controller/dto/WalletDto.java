package be.brickbit.lpm.catering.controller.dto;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class WalletDto {
    private BigDecimal amount;
}
