package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.AbstractRepoIT;
import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.fixture.StockFlowFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class StockFlowRepositoryIT extends AbstractRepoIT {
    @Autowired
    private StockFlowRepository stockFlowRepository;

    @Test
    public void countByDetailsStockProduct() throws Exception {
        StockFlow stockFlow = StockFlowFixture.getStockFlow();

        insert(
                stockFlow.getDetails().get(0).getStockProduct(),
                stockFlow
        );

        Integer result = stockFlowRepository.countByDetailsStockProduct(stockFlow.getDetails().get(0).getStockProduct());

        assertThat(result).isEqualTo(1);
    }

}