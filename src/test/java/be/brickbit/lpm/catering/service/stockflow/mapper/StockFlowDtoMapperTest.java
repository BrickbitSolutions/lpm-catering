package be.brickbit.lpm.catering.service.stockflow.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.fixture.StockFlowFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockFlowDtoMapperTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private StockFlowDtoMapper mapper;

    @Test
    public void testMap() throws Exception {
        StockFlow stockFlow = StockFlowFixture.getStockFlow();
        UserDetailsDto someUser = UserFixture.mutable();

        when(userService.findOne(stockFlow.getUserId())).thenReturn(someUser);

        StockFlowDto dto = mapper.map(stockFlow);

        assertThat(dto.getId()).isEqualTo(stockFlow.getId());
        assertThat(dto.getTimestamp()).isEqualTo(stockFlow.getTimestamp().format(DateUtils.getDateFormat()));
        assertThat(dto.getUsername()).isEqualTo(someUser.getUsername());
        assertThat(dto.getType()).isEqualTo(stockFlow.getStockFlowType());
        assertThat(dto.getLevel()).isEqualTo(stockFlow.getLevel());
        assertThat(dto.getStockFlowDetails().size()).isEqualTo(stockFlow.getDetails().size());
        assertThat(dto.getStockFlowDetails().get(0).getProductName()).isEqualTo(stockFlow.getDetails().get(0).getStockProduct().getName());
        assertThat(dto.getStockFlowDetails().get(0).getQuantity()).isEqualTo(stockFlow.getDetails().get(0).getQuantity());
    }
}