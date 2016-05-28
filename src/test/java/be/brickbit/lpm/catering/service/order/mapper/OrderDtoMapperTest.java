package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.OrderLineDtoFixture;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.order.dto.OrderDto;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.core.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

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

    @Test
    public void testMap() throws Exception {
        Order order = OrderFixture.getOrder();

        User cateringAdmin = UserFixture.getCateringAdmin();
        when(userRepository.findOne(order.getUserId())).thenReturn(cateringAdmin);
        when(orderLineDtoMapper.map(any(OrderLine.class))).thenReturn(OrderLineDtoFixture.mutable());

        OrderDto result = mapper.map(order);

        assertThat(result.getTotalPrice()).isEqualTo(new BigDecimal(11));
        assertThat(result.getId()).isEqualTo(order.getId());
        assertThat(result.getStatus()).isEqualTo(order.getOrderStatus());
        assertThat(result.getTimestamp()).isEqualTo(order.getTimestamp().format(DateUtils.getDateFormat()));
        assertThat(result.getUsername()).isEqualTo(cateringAdmin.getUsername());
        assertThat(result.getOrderLines().size()).isEqualTo(order.getOrderLines().size());
    }
}