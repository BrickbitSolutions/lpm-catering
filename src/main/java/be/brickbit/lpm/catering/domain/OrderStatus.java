package be.brickbit.lpm.catering.domain;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatus {
    QUEUED(0),
    CANCELED(1),
    IN_PROGRESS(2),
    READY(3),
    COMPLETED(4);

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
            throw new RuntimeException("OrderStatus does not exist.");
        }
    }
}
