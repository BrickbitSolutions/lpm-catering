package be.brickbit.lpm.catering.service.order.mapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.OrderLineDtoFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.order.dto.OrderDetailDto;
import be.brickbit.lpm.catering.service.order.util.PriceUtil;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailDtoMapperTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private OrderLineDtoMapper orderLineDtoMapper;

    @InjectMocks
    private OrderDetailDtoMapper mapper;

    @Test
    public void testMap() throws Exception {
        Order order = OrderFixture.mutable();

        when(orderLineDtoMapper.map(any(OrderLine.class))).thenReturn(OrderLineDtoFixture.mutable());

        OrderDetailDto result = mapper.map(order);

        assertThat(result.getTotalPrice()).isEqualTo(PriceUtil.calculateTotalPrice(order));
        assertThat(result.getId()).isEqualTo(order.getId());
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(result.getTimestamp()).isEqualTo(order.getTimestamp().format(DateUtils.getDateTimeFormat()));
        assertThat(result.getUserId()).isEqualTo(order.getUserId());
        assertThat(result.getOrderLines().size()).isEqualTo(order.getOrderLines().size());
        assertThat(result.getComment()).isEqualTo(order.getComment());
        assertThat(result.getReservationDetails()).isNull();
    }

    @Test
    public void mapsReservationDetailsHoldUntilYesterday() throws Exception {
        Order order = OrderFixture.mutable();
        order.setHoldUntil(LocalDate.now().minusDays(1));
        order.getOrderLines().forEach(orderLine -> orderLine.setStatus(OrderStatus.CREATED));

        when(orderLineDtoMapper.map(any(OrderLine.class))).thenReturn(OrderLineDtoFixture.mutable());

        OrderDetailDto result = mapper.map(order);

        assertThat(result.getReservationDetails()).isNotNull();
        assertThat(result.getReservationDetails().getHoldUntil()).isEqualTo(order.getHoldUntil()
                .format(DateUtils.getDateFormat()));
        assertThat(result.getReservationDetails().getHoldExpired()).isTrue();
    }

    @Test
    public void mapsReservationDetails() throws Exception {
        Order order = OrderFixture.mutable();
        order.setHoldUntil(LocalDate.now());
        order.getOrderLines().forEach(orderLine -> orderLine.setStatus(OrderStatus.CREATED));

        when(orderLineDtoMapper.map(any(OrderLine.class))).thenReturn(OrderLineDtoFixture.mutable());

        OrderDetailDto result = mapper.map(order);

        assertThat(result.getReservationDetails()).isNotNull();
        assertThat(result.getReservationDetails().getHoldUntil()).isEqualTo(order.getHoldUntil()
                .format(DateUtils.getDateFormat()));
        assertThat(result.getReservationDetails().getHoldExpired()).isTrue();
    }

    @Test
    public void mapsReservationDetailsButReservationIsNotClaimable() throws Exception {
        Order order = OrderFixture.mutable();
        order.setHoldUntil(LocalDate.now().plusDays(1));
        order.getOrderLines().forEach(orderLine -> orderLine.setStatus(OrderStatus.CREATED));

        when(orderLineDtoMapper.map(any(OrderLine.class))).thenReturn(OrderLineDtoFixture.mutable());

        OrderDetailDto result = mapper.map(order);

        assertThat(result.getReservationDetails()).isNotNull();
        assertThat(result.getReservationDetails().getHoldUntil()).isEqualTo(order.getHoldUntil()
                .format(DateUtils.getDateFormat()));
        assertThat(result.getReservationDetails().getHoldExpired()).isFalse();
    }

    @Test
    public void mapsReservationDetailsButReservationIsQueued() throws Exception {
        Order order = OrderFixture.mutable();
        order.setHoldUntil(LocalDate.now().minusDays(1));
        order.getOrderLines().forEach(orderLine -> orderLine.setStatus(OrderStatus.QUEUED));

        when(orderLineDtoMapper.map(any(OrderLine.class))).thenReturn(OrderLineDtoFixture.mutable());

        OrderDetailDto result = mapper.map(order);

        assertThat(result.getReservationDetails()).isNotNull();
        assertThat(result.getReservationDetails().getHoldUntil()).isEqualTo(order.getHoldUntil()
                .format(DateUtils.getDateFormat()));
        assertThat(result.getReservationDetails().getHoldExpired()).isFalse();
    }
}