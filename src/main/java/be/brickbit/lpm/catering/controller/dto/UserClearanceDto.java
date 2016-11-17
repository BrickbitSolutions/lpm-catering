package be.brickbit.lpm.catering.controller.dto;

import java.util.List;

import be.brickbit.lpm.catering.domain.ClearanceType;
import lombok.Value;

@Value
public class UserClearanceDto {
    List<ClearanceType> clearance;
}
