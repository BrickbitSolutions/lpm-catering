package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import org.assertj.core.util.Lists;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDtoFixture {
    public static OrderDto getOrderDto(){
        return new OrderDto(1L, new BigDecimal(11), LocalDateTime.now().toString(), "jay", OrderStatus.QUEUED, Lists
                .newArrayList(OrderLineDtoFixture.mutable()));
    }
}
