package be.brickbit.lpm.catering.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.brickbit.lpm.catering.AbstractRepoIT;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.fixture.ProductFixture;

public class ProductRepositoryIT extends AbstractRepoIT {
	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testFindEnabledByProductType() throws Exception {
        Product newProduct = ProductFixture.getPizza();
        Product newProductReservation = ProductFixture.getPizza();
        newProductReservation.setReservationOnly(true);
        Product disabledProduct = ProductFixture.getPizza();
        disabledProduct.setAvailable(false);

        insert(
                newProduct.getReceipt().get(0).getStockProduct(),
                newProduct,
                newProductReservation.getReceipt().get(0).getStockProduct(),
                newProductReservation,
                disabledProduct.getReceipt().get(0).getStockProduct(),
                disabledProduct
        );

		List<Product> result = productRepository.findByProductTypeAndAvailableTrueAndReservationOnlyFalse(ProductType.FOOD);

		assertThat(result).containsOnly(newProduct);
	}

    @Test
    public void testFindByProductType() throws Exception {
        Product newProduct = ProductFixture.getPizza();
        Product disabledProduct = ProductFixture.getPizza();
        disabledProduct.setAvailable(false);

        insert(
                newProduct.getReceipt().get(0).getStockProduct(),
                newProduct,
                disabledProduct.getReceipt().get(0).getStockProduct(),
                disabledProduct
        );

        List<Product> result = productRepository.findByProductType(ProductType.FOOD);

        assertThat(result).containsOnly(newProduct, disabledProduct);
    }

	@Test
	public void testSaveProduct() throws Exception {
        Product newProduct = ProductFixture.getPizza();
        insert(
                newProduct.getReceipt().get(0).getStockProduct(),
                newProduct
        );

		Product result = productRepository.findOne(newProduct.getId());
		assertThat(result).isEqualTo(result);
	}

    @Test
    public void findAllQueueNames() throws Exception {
        Product product = ProductFixture.getPizza();
        Product otherProduct = ProductFixture.getPizza();
        otherProduct.getPreparation().setQueueName("SomeOtherQueueName");
        insert(
                product.getReceipt().get(0).getStockProduct(),
                product.getPreparation(),
                product,
                otherProduct.getReceipt().get(0).getStockProduct(),
                otherProduct.getPreparation(),
                otherProduct
        );

        List<String> queueNames = productRepository.findAllQueueNames();

        assertThat(queueNames).contains(product.getPreparation().getQueueName(), otherProduct.getPreparation().getQueueName());

    }

    @Test
    public void countProductsUsingStockProduct() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProductPizza();
        insert(stockProduct);

        Product product = ProductFixture.getPizza();
        product.getReceipt().get(0).setStockProduct(stockProduct);
        Product otherProduct = ProductFixture.getPizza();
        otherProduct.getReceipt().get(0).setStockProduct(stockProduct);
        insert(
                product.getPreparation(),
                product,
                otherProduct.getPreparation(),
                otherProduct
        );

        Integer count = productRepository.countByReceiptStockProductId(stockProduct.getId());

        assertThat(count).isEqualTo(2);
    }
}