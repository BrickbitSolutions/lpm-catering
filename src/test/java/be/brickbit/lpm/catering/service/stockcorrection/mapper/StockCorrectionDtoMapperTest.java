package be.brickbit.lpm.catering.service.stockcorrection.mapper;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.fixture.StockCorrectionFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.core.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockCorrectionDtoMapperTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StockCorrectionDtoMapper mapper;

    @Test
    public void testMap() throws Exception {
        StockCorrection stockCorrection = StockCorrectionFixture.getStockCorrection();
        User cateringAdmin = UserFixture.getCateringAdmin();
        when(userRepository.findOne(stockCorrection.getUserId())).thenReturn(cateringAdmin);

        StockCorrectionDto dto = mapper.map(stockCorrection);

        assertThat(dto.getProductName()).isEqualTo(stockCorrection.getStockProduct().getName());
        assertThat(dto.getQuantity()).isEqualTo(stockCorrection.getQuantity());
        assertThat(dto.getTimestamp()).isEqualTo(stockCorrection.getTimestamp());
        assertThat(dto.getUserName()).isEqualTo(cateringAdmin.getUsername());
    }
}