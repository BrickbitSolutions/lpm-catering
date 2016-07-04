package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.service.order.dto.OrderLineDto;

public class OrderLineDtoFixture {
    public static OrderLineDto mutable() {
        return new OrderLineDto(
                1L,
                1,
                "Jupiler 33cl",
                OrderStatus.CREATED
        );
    }
}
