package be.brickbit.lpm.catering.service.stockcorrection;

import be.brickbit.lpm.catering.domain.StockCorrection;
import be.brickbit.lpm.catering.fixture.StockCorrectionCommandFixture;
import be.brickbit.lpm.catering.fixture.StockCorrectionDtoFixture;
import be.brickbit.lpm.catering.fixture.StockCorrectionFixture;
import be.brickbit.lpm.catering.repository.StockCorrectionRepository;
import be.brickbit.lpm.catering.service.stockcorrection.command.NewStockCorrectionCommand;
import be.brickbit.lpm.catering.service.stockcorrection.dto.StockCorrectionDto;
import be.brickbit.lpm.catering.service.stockcorrection.mapper.NewStockCorrectionMapper;
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
    private NewStockCorrectionMapper newStockCorrectionMapper;

    @InjectMocks
    private StockCorrectionService stockCorrectionService;

    @Test
    public void testSaveStockCorrection() throws Exception {
        NewStockCorrectionCommand command = StockCorrectionCommandFixture.getNewStockCorrectionCommand();
        StockCorrection stockCorrection = StockCorrectionFixture.getStockCorrection();
        User user = new User();

        when(newStockCorrectionMapper.map(command)).thenReturn(stockCorrection);
        stockCorrectionService.saveStockCorrection(command, user);

        verify(stockCorrectionRepository).save(stockCorrection);
    }

    @Test
    public void testFindAll() throws Exception {
        StockCorrection stockCorrection = StockCorrectionFixture.getStockCorrection();
        List<StockCorrection> stockCorrections = Collections.singletonList(stockCorrection);
        StockCorrectionDto dto = StockCorrectionDtoFixture.getStockCorrectionDto();

        when(stockCorrectionRepository.findAll()).thenReturn(stockCorrections);
        when(stockCorrectionDtoMapper.map(stockCorrection)).thenReturn(dto);

        List<StockCorrectionDto> result = stockCorrectionService.findAll();

        assertThat(result.size()).isEqualTo(stockCorrections.size());
        assertThat(result.get(0)).isSameAs(dto);
    }
}