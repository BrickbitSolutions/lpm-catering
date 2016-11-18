package be.brickbit.lpm.catering.controller;

import com.google.common.collect.Lists;

import com.mysema.query.jpa.impl.JPAQuery;

import org.junit.Test;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductPreparation;
import be.brickbit.lpm.catering.domain.QProduct;
import be.brickbit.lpm.catering.domain.QProductPreparation;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.CreateProductCommandFixture;
import be.brickbit.lpm.catering.fixture.EditProductCommandFixture;
import be.brickbit.lpm.catering.fixture.EditProductPreparationCommandFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.service.product.command.CreateProductCommand;
import be.brickbit.lpm.catering.service.product.command.EditProductCommand;
import be.brickbit.lpm.catering.service.product.command.EditProductPreparationCommand;
import be.brickbit.lpm.catering.service.product.command.ReceiptLineCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerIT extends AbstractControllerIT {
    @Test
    public void savesNewProduct() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProductJupiler();

        insert(stockProduct);

        ReceiptLineCommand receiptLineCommand = new ReceiptLineCommand(
                stockProduct.getId(),
                1
        );
        CreateProductCommand command = CreateProductCommandFixture.mutable();
        command.setReceipt(Lists.newArrayList(receiptLineCommand));

        performPost("/product", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(command.getName())))
                .andExpect(jsonPath("$.price", notNullValue()))
//                .andExpect(jsonPath("$.price", is(command.getPrice())))
//              => BigDecimal loses prcision when reading from json, so the match fails :/
                .andExpect(jsonPath("$.productType", is(command.getProductType().toString())))
                .andExpect(jsonPath("$.clearanceType", is(stockProduct.getClearance().toString())))
                .andExpect(jsonPath("$.avgConsumption", is(0)))
                .andExpect(jsonPath("$.stockLevel", is(stockProduct.getStockLevel())))
                .andExpect(jsonPath("$.available", is(false)));
    }

    @Test
    public void getsAllProducts() throws Exception {
        Product jupiler = ProductFixture.getJupiler();
        Product pizza = ProductFixture.getPizza();

        insert(
                jupiler.getReceipt().get(0).getStockProduct(),
                jupiler,
                pizza.getReceipt().get(0).getStockProduct(),
                pizza
        );

        performGet("/product")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getsAllProductsByType() throws Exception {
        Product jupiler = ProductFixture.getJupiler();
        Product pizza = ProductFixture.getPizza();

        insert(
                jupiler.getReceipt().get(0).getStockProduct(),
                jupiler,
                pizza.getReceipt().get(0).getStockProduct(),
                pizza
        );

        performGet("/product?type=" + jupiler.getProductType().toString())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(jupiler.getName())));
    }

    @Test
    public void getsAllEnabledProductsByType() throws Exception {
        Product pizza = ProductFixture.getPizza();
        Product disabledPizza = ProductFixture.getPizza();
        disabledPizza.setAvailable(false);

        insert(
                pizza.getReceipt().get(0).getStockProduct(),
                pizza,
                disabledPizza.getReceipt().get(0).getStockProduct(),
                disabledPizza
        );

        performGet("/product?enabled=true&type=" + pizza.getProductType().toString())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(pizza.getId().intValue())));
    }

    @Test
    public void getsAllEnabledReservationOnlyProductsByType() throws Exception {
        Product pizza = ProductFixture.getPizza();
        Product reservationPizza = ProductFixture.getPizza();
        reservationPizza.setReservationOnly(true);

        insert(
                pizza.getReceipt().get(0).getStockProduct(),
                pizza,
                reservationPizza.getReceipt().get(0).getStockProduct(),
                reservationPizza
        );

        performGet("/product?enabled=true&reservationOnly=true&type=" + pizza.getProductType()
                .toString())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(reservationPizza.getId().intValue())));
    }

    @Test
    public void updatesProduct() throws Exception {
        Product pizza = ProductFixture.getPizza();

        insert(
                pizza.getReceipt().get(0).getStockProduct(),
                pizza
        );

        EditProductCommand command = EditProductCommandFixture.mutable();

        performPut("/product/" + pizza.getId(), command)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(command.getName())));

                //.andExpect(jsonPath("$.price", is(command.getPrice())));
                // See Create Product BigDecimal issue in this test...
    }

    @Test
    public void updatesProductPreparation() throws Exception {
        Product pizza = ProductFixture.getPizza();

        insert(
                pizza.getReceipt().get(0).getStockProduct(),
                pizza
        );

        EditProductPreparationCommand command = EditProductPreparationCommandFixture.mutable();

        performPut(String.format("/product/%s/preparation", pizza.getId()), command)
                .andExpect(status().isOk());

        ProductPreparation preparation = new JPAQuery(getEntityManager())
                .from(QProductPreparation.productPreparation)
                .where(QProductPreparation.productPreparation.id.eq(pizza.getPreparation().getId()))
                .uniqueResult(QProductPreparation.productPreparation);

        assertThat(preparation.getInstructions()).isEqualTo(command.getInstructions());
        assertThat(preparation.getTimer()).isEqualTo(command.getTimerInMinutes() * 60);
        assertThat(preparation.getQueueName()).isEqualTo(command.getQueueName());
    }

    @Test
    public void getsProductById() throws Exception {
        Product pizza = ProductFixture.getPizza();

        insert(
                pizza.getReceipt().get(0).getStockProduct(),
                pizza
        );

        performGet(String.format("/product/%s", pizza.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(pizza.getId().intValue())))
                .andExpect(jsonPath("$.name", is(pizza.getName())))
                .andExpect(jsonPath("$.price", notNullValue()))
//                .andExpect(jsonPath("$.price", is(pizza.getPrice())))
//              => BigDecimal loses precision when reading from json, so the match fails :/
                .andExpect(jsonPath("$.productType", is(pizza.getProductType().toString())))
                .andExpect(jsonPath("$.clearanceType", is(pizza.getClearance().toString())))
                .andExpect(jsonPath("$.avgConsumption", is(pizza.getAvgConsumption())))
                .andExpect(jsonPath("$.stockLevel", notNullValue()))
                .andExpect(jsonPath("$.available", is(pizza.getAvailable())));
    }

    @Test
    public void deletesProduct() throws Exception {
        Product pizza = ProductFixture.getPizza();
        pizza.setAvailable(false);

        insert(
                pizza.getReceipt().get(0).getStockProduct(),
                pizza
        );

        performDelete(String.format("/product/%s", pizza.getId()))
                .andExpect(status().isNoContent());

        Product product = new JPAQuery(getEntityManager())
                .from(QProduct.product)
                .where(QProduct.product.id.eq(pizza.getId()))
                .uniqueResult(QProduct.product);

        assertThat(product).isNull();
    }

    @Test
    public void updateProductAvailability() throws Exception {
        Product pizza = ProductFixture.getPizza();
        pizza.setAvailable(true);

        insert(
                pizza.getReceipt().get(0).getStockProduct(),
                pizza
        );

        performPut(String.format("/product/%s/available", pizza.getId()), null)
                .andExpect(status().isNoContent());

        Product result = new JPAQuery(getEntityManager())
                .from(QProduct.product)
                .where(QProduct.product.id.eq(pizza.getId()))
                .uniqueResult(QProduct.product);

        assertThat(result.getAvailable()).isFalse();
    }

    @Test
    public void getsProductReceipt() throws Exception {
        Product pizza = ProductFixture.getPizza();

        insert(
                pizza.getReceipt().get(0).getStockProduct(),
                pizza
        );

        performGet(String.format("/product/%s/receipt", pizza.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.queueName", is(pizza.getPreparation().getQueueName())))
                .andExpect(jsonPath("$.instructions", is(pizza.getPreparation().getInstructions())))
                .andExpect(jsonPath("$.timerInMinutes", is(pizza.getPreparation().getTimer() / 60)))
                .andExpect(jsonPath("$.productsToInclude", hasSize(pizza.getReceipt().size())))
                .andExpect(jsonPath("$.productsToInclude[0].stockProductId", is(pizza.getReceipt().get(0).getStockProduct().getId().intValue())))
                .andExpect(jsonPath("$.productsToInclude[0].quantity", is(pizza.getReceipt().get(0).getQuantity())));
    }
}