package be.brickbit.lpm.catering.service.product.mapper;

import com.google.common.collect.Lists;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.EditProductCommandFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.product.command.EditProductCommand;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductMergerTest {

    @Mock
    private StockProductRepository stockProductRepository;

    @InjectMocks
    private ProductMerger merger;

    @Test
    public void updatesPrice() throws Exception {
        Product product = ProductFixture.getJupiler();
        EditProductCommand command = EditProductCommandFixture.mutable();

        merger.merge(command, product);

        assertThat(product.getPrice()).isEqualTo(command.getPrice());
    }

    @Test
    public void mergesProductName() throws Exception {
        Product product = ProductFixture.getJupiler();

        EditProductCommand command = EditProductCommandFixture.mutable();

        merger.merge(command, product);

        assertThat(product.getName()).isEqualTo(command.getName());
    }

    @Test
    public void updatesReservationOnly() throws Exception {
        Product product = ProductFixture.getJupiler();

        EditProductCommand command = EditProductCommandFixture.mutable();
        command.setReservationOnly(!product.getReservationOnly());

        merger.merge(command, product);

        assertThat(product.getReservationOnly()).isEqualTo(command.getReservationOnly());
    }

    @Test
    public void mergesSupplements() throws Exception {
        Product product = ProductFixture.getPizza();
        StockProduct supplement = StockProductFixture.getStockProductCola();
        Long supplementId = randomLong();

        EditProductCommand command = EditProductCommandFixture.mutable();
        command.setSupplements(Lists.newArrayList(supplementId));

        when(stockProductRepository.findOne(supplementId)).thenReturn(supplement);

        merger.merge(command, product);

        assertThat(product.getSupplements()).containsExactly(supplement);
    }

    @Test
    public void removesSupplements() throws Exception {
        Product product = ProductFixture.getPizza();

        assertThat(product.getSupplements().size()).isGreaterThan(0);
        EditProductCommand command = EditProductCommandFixture.mutable();
        command.setSupplements(Lists.newArrayList());

        merger.merge(command, product);

        assertThat(product.getSupplements()).hasSize(0);
    }
}