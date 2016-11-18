package be.brickbit.lpm.catering.service.wallet.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
public class WalletDto {
    private BigDecimal amount;
}
