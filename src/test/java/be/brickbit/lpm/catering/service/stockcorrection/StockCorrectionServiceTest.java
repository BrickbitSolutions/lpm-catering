package be.brickbit.lpm.catering.service.stockcorrection;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.fixture.StockCorrectionCommandFixture;
import be.brickbit.lpm.catering.fixture.StockCorrectionDtoFixture;
import be.brickbit.lpm.catering.fixture.StockCorrectionFixture;
import be.brickbit.lpm.catering.repository.StockCorrectionRepository;
import be.brickbit.lpm.catering.service.stockcorrection.command.StockCorrectionCommand;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.StockCorrectionCommandToEntityMapper;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.StockCorrectionDtoMapper;
import be.brickbit.lpm.core.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(value = MockitoJUnitRunner.class)
public class StockCorrectionServiceTest {

    @Mock
    private StockCorrectionRepository stockCorrectionRepository;

    @Mock
    private StockCorrectionDtoMapper stockCorrectionDtoMapper;

    @Mock
    private StockCorrectionCommandToEntityMapper stockCorrectionCommandToEntityMapper;

    @InjectMocks
    private StockCorrectionService stockCorrectionService;

    @Test
    public void testSaveStockCorrection() throws Exception {
        StockCorrectionCommand command = StockCorrectionCommandFixture.getNewStockCorrectionCommand();
        StockCorrection stockCorrection = StockCorrectionFixture.getStockCorrection();
        StockCorrectionDto dto = StockCorrectionDtoFixture.getStockCorrectionDto();
        User user = new User();

        when(stockCorrectionCommandToEntityMapper.map(command)).thenReturn(stockCorrection);
        when(stockCorrectionDtoMapper.map(stockCorrection)).thenReturn(dto);
        StockCorrectionDto result = stockCorrectionService.save(command, user, stockCorrectionDtoMapper);

        verify(stockCorrectionRepository).save(stockCorrection);
        assertThat(result).isSameAs(dto);
    }

    @Test
    public void testFindAll() throws Exception {
        StockCorrection stockCorrection = StockCorrectionFixture.getStockCorrection();
        List<StockCorrection> stockCorrections = Collections.singletonList(stockCorrection);
        StockCorrectionDto dto = StockCorrectionDtoFixture.getStockCorrectionDto();

        when(stockCorrectionRepository.findAll()).thenReturn(stockCorrections);
        when(stockCorrectionDtoMapper.map(stockCorrection)).thenReturn(dto);

        List<StockCorrectionDto> result = stockCorrectionService.findAll(stockCorrectionDtoMapper);

        assertThat(result.size()).isEqualTo(stockCorrections.size());
        assertThat(result.get(0)).isSameAs(dto);
    }
}