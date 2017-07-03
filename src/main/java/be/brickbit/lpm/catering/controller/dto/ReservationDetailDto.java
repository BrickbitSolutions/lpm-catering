package be.brickbit.lpm.catering.controller.dto;

import lombok.Value;

@Value
public class ReservationDetailDto {
    private String holdUntil;
    private Boolean holdExpired;
}
