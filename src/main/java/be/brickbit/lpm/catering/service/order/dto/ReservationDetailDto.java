package be.brickbit.lpm.catering.service.order.dto;

import lombok.Value;

@Value
public class ReservationDetailDto {
    private String holdUntil;
    private Boolean holdExpired;
}
