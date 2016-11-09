package be.brickbit.lpm.catering.service.product.mapper;

import org.junit.Before;
import org.junit.Test;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.fixture.EditProductCommandFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.service.product.command.EditProductCommand;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductMergerTest {

    private ProductMerger merger;

    @Before
    public void setUp() throws Exception {
        merger = new ProductMerger();
    }

    @Test
    public void updatesPrice() throws Exception {
        Product product = ProductFixture.getJupiler();
        EditProductCommand command = EditProductCommandFixture.mutable();

        merger.merge(command, product);

        assertThat(product.getPrice()).isEqualTo(command.getPrice());
    }

    @Test
    public void mergesProduct__UpdatesName() throws Exception {
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
}