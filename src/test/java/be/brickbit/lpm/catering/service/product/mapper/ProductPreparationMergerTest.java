package be.brickbit.lpm.catering.service.product.mapper;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.service.product.command.EditProductPreparationCommand;
import org.junit.Before;
import org.junit.Test;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductPreparationMergerTest {
    private ProductPreparationMerger merger;

    @Before
    public void setUp() throws Exception {
        merger = new ProductPreparationMerger();
    }

    @Test
    public void mapsQueueName() throws Exception {
        EditProductPreparationCommand command = new EditProductPreparationCommand();
        command.setQueueName(randomString());

        Product product = ProductFixture.getPizza();

        merger.merge(command, product);

        assertThat(product.getPreparation().getQueueName()).isEqualTo(command.getQueueName());
    }

    @Test
    public void mapsInstructions() throws Exception {
        EditProductPreparationCommand command = new EditProductPreparationCommand();
        command.setInstructions(randomString());

        Product product = ProductFixture.getPizza();

        merger.merge(command, product);

        assertThat(product.getPreparation().getInstructions()).isEqualTo(command.getInstructions());
    }

    @Test
    public void mapsTimer() throws Exception {
        EditProductPreparationCommand command = new EditProductPreparationCommand();
        command.setTimerInMinutes(randomInt());

        Product product = ProductFixture.getPizza();

        merger.merge(command, product);

        assertThat(product.getPreparation().getTimer()).isEqualTo(command.getTimerInMinutes() * 60);
    }

    @Test
    public void createsNewPreparation() throws Exception {
        EditProductPreparationCommand command = new EditProductPreparationCommand();

        Product product = ProductFixture.getPizza();
        product.setPreparation(null);

        merger.merge(command, product);

        assertThat(product.getPreparation()).isNotNull();
    }
}