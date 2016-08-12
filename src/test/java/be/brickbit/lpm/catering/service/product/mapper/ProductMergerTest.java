package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.service.product.command.EditProductCommand;
import org.junit.Before;
import org.junit.Test;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductMergerTest {

    private ProductMerger merger;

    @Before
    public void setUp() throws Exception {
        merger = new ProductMerger();
    }

    @Test
    public void mergesProduct__UpdatesName() throws Exception {
        Product product = ProductFixture.getJupiler();

        EditProductCommand command = new EditProductCommand(randomString(), product.getClearance());

        merger.merge(command, product);

        assertThat(product.getName()).isEqualTo(command.getName());
    }

    @Test
    public void mergesProduct__UpdatesClearance() throws Exception {
        Product product = ProductFixture.getJupiler();

        EditProductCommand command = new EditProductCommand(product.getName(), ClearanceType.from(randomInt(0, 3)));

        merger.merge(command, product);

        assertThat(product.getClearance()).isEqualTo(command.getClearance());
    }
}