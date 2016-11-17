package be.brickbit.lpm.catering.domain;

import be.brickbit.lpm.infrastructure.exception.ServiceException;

import java.util.Arrays;
import java.util.Optional;

public enum ClearanceType {
    PLUS_18(18),
    PLUS_16(16),
    PLUS_21(21),
    ANY(0);

    private Integer clearanceLevel;

    ClearanceType(Integer clearanceLevel) {
        this.clearanceLevel = clearanceLevel;
    }

    public Integer getClearanceLevel() {
        return clearanceLevel;
    }

    public static ClearanceType from(Integer clearanceLevel){
        Optional<ClearanceType> clearanceType = Arrays.asList(values()).stream().filter(ct -> ct.getClearanceLevel().equals(clearanceLevel)).findFirst();
        if(clearanceType.isPresent()){
            return clearanceType.get();
        }else{
            throw new ServiceException("Clearance does not exist.");
        }
    }
}
