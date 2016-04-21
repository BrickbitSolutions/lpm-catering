package be.brickbit.lpm.catering.repository;

import static org.assertj.core.api.Assertions.assertThat;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.brickbit.lpm.catering.AbstractRepoIT;
import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;

import java.util.List;

public class StockProductRepositoryTest extends AbstractRepoIT {
    @Autowired(required = true)
    private StockProductRepository stockProductRepository;

    @Test
    public void testFindByProductTypeAndClearance() throws Exception {
        StockProduct lasagna = StockProductFixture.getStockProductLasagna();
        StockProduct pizza = StockProductFixture.getStockProductPizza();
        StockProduct cola = StockProductFixture.getStockProductCola();
        StockProduct jupiler = StockProductFixture.getStockProductJupiler();

        insert(
                lasagna,
                pizza,
                cola,
                jupiler
        );

        List<StockProduct> result = stockProductRepository.findByProductTypeAndClearance(ProductType.DRINKS, ClearanceType.PLUS_16);
        assertThat(result).hasSize(1);
        assertThat(result).contains(jupiler);
    }

    @Test
    public void voidTestByProductType() throws Exception {
        StockProduct lasagna = StockProductFixture.getStockProductLasagna();
        StockProduct pizza = StockProductFixture.getStockProductPizza();
        StockProduct cola = StockProductFixture.getStockProductCola();
        StockProduct jupiler = StockProductFixture.getStockProductJupiler();

        insert(
                lasagna,
                pizza,
                cola,
                jupiler
        );

        List<StockProduct> result = stockProductRepository.findByProductType(ProductType.DRINKS);
        assertThat(result).hasSize(2);
        assertThat(result).contains(cola);
        assertThat(result).contains(jupiler);
    }
}