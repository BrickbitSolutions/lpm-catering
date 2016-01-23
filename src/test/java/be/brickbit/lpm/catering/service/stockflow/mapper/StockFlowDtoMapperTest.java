package be.brickbit.lpm.catering.service.stockflow.mapper;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.fixture.StockFlowFixture;
import be.brickbit.lpm.catering.fixture.UserDtoFixture;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.catering.service.user.dto.UserDto;
import be.brickbit.lpm.catering.service.user.mapper.UserDtoMapper;
import be.brickbit.lpm.core.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockFlowDtoMapperTest {

    @Mock
    private IUserService userService;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private StockFlowDtoMapper mapper;

    @Test
    public void testMap() throws Exception {
        StockFlow stockFlow = StockFlowFixture.getStockFlow();
        UserDto userDto = UserDtoFixture.getUserDto();

        when(userService.findOne(stockFlow.getUserId(), userDtoMapper)).thenReturn(userDto);

        StockFlowDto dto = mapper.map(stockFlow);

        assertThat(dto.getId()).isEqualTo(stockFlow.getId());
        assertThat(dto.getTimestamp()).isEqualTo(stockFlow.getTimestamp());
        assertThat(dto.getIncluded()).isEqualTo(stockFlow.getIncluded());
        assertThat(dto.getUsername()).isEqualTo(userDto.getUsername());
        assertThat(dto.getStockFlowDetails().size()).isEqualTo(stockFlow.getDetails().size());
        assertThat(dto.getStockFlowDetails().get(0).getProductName()).isEqualTo(stockFlow.getDetails().get(0).getStockProduct().getName());
        assertThat(dto.getStockFlowDetails().get(0).getQuantity()).isEqualTo(stockFlow.getDetails().get(0).getQuantity());
        assertThat(dto.getStockFlowDetails().get(0).getPricePerUnit()).isEqualTo(stockFlow.getDetails().get(0).getPrice());
    }
}