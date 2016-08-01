package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.OrderLineDtoFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.service.order.util.PriceUtil;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.core.repository.UserRepository;
import be.brickbit.lpm.infrastructure.exception.ServiceException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDtoMapperTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderLineDtoMapper orderLineDtoMapper;

    @InjectMocks
    private OrderDtoMapper mapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testMap() throws Exception {
        Order order = OrderFixture.mutable();

        User cateringAdmin = UserFixture.getCateringAdmin();
        when(userRepository.findOne(order.getUserId())).thenReturn(cateringAdmin);
        when(orderLineDtoMapper.map(any(OrderLine.class))).thenReturn(OrderLineDtoFixture.mutable());

        OrderDto result = mapper.map(order);

        assertThat(result.getTotalPrice()).isEqualTo(PriceUtil.calculateTotalPrice(order));
        assertThat(result.getId()).isEqualTo(order.getId());
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(result.getTimestamp()).isEqualTo(order.getTimestamp().format(DateUtils.getDateFormat()));
        assertThat(result.getUsername()).isEqualTo(cateringAdmin.getUsername());
        assertThat(result.getSeatNumber()).isEqualTo(cateringAdmin.getSeatNumber());
        assertThat(result.getOrderLines().size()).isEqualTo(order.getOrderLines().size());
        assertThat(result.getComment()).isEqualTo(order.getComment());
    }
}