package be.brickbit.lpm.catering.domain;

import be.brickbit.lpm.infrastructure.exception.ServiceException;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {
    CREATED(0),
    QUEUED(1),
    CANCELED(2),
    IN_PROGRESS(3),
    READY(4),
    COMPLETED(5);

    private Integer statusLevel;

    OrderStatus(Integer statusLevel) {
        this.statusLevel = statusLevel;
    }

    public Integer getStatusLevel() {
        return statusLevel;
    }

    public static OrderStatus from(Integer statusLevel){
        Optional<OrderStatus> orderStatus = Arrays.asList(values()).stream().filter(ct -> ct.getStatusLevel().equals(statusLevel)).findFirst();
        if(orderStatus.isPresent()){
            return orderStatus.get();
        }else{
            throw new ServiceException("OrderStatus does not exist.");
        }
    }
}
