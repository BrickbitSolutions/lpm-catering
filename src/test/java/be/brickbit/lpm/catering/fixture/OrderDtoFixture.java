package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.dto.OrderDetailDto;
import org.assertj.core.util.Lists;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDtoFixture {
    public static OrderDetailDto mutable(){
        return new OrderDetailDto(1L, new BigDecimal(11), LocalDateTime.now().toString(), "jay", 1, OrderStatus.QUEUED, Lists
                .newArrayList(OrderLineDtoFixture.mutable()), "I've got your name written in a rose tattoo :)");
    }
}
