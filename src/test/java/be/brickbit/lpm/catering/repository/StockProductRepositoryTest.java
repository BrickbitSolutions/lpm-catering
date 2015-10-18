package be.brickbit.lpm.catering.repository;

import be.brickbit.lpm.catering.AbstractRepoIT;
import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseSetup(
        value = "classpath:db-unit/stockproduct.xml",
        type = DatabaseOperation.CLEAN_INSERT
)
public class StockProductRepositoryTest extends AbstractRepoIT {

    @Autowired(required = true)
    private StockProductRepository stockProductRepository;

    @Test
    public void testFindByProductTypeAndClearance() throws Exception {
        assertThat(stockProductRepository.findByProductTypeAndClearance(ProductType.DRINKS, ClearanceType.PLUS_16).size()).isEqualTo(1);
    }
}