package be.brickbit.lpm.catering.controller.mapper;

import be.brickbit.lpm.catering.controller.mapper.OrderLineDtoMapper;
import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.fixture.OrderLineFixture;
import be.brickbit.lpm.catering.controller.dto.OrderLineDto;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderLineDtoMapperTest {
    private OrderLineDtoMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new OrderLineDtoMapper();
    }

    @Test
    public void map() throws Exception {
        OrderLine source = OrderLineFixture.getJupilerOrderLine();

        OrderLineDto result = mapper.map(source);

        assertThat(result.getId()).isEqualTo(source.getId());
        assertThat(result.getProduct()).isEqualTo(source.getProduct().getName());
        assertThat(result.getQuantity()).isEqualTo(source.getQuantity());
        assertThat(result.getStatus()).isEqualTo(source.getStatus());
    }
}