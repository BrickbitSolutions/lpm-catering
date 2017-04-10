package be.brickbit.lpm.catering.controller;

import com.mysema.query.jpa.impl.JPAQuery;

import org.junit.Test;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.domain.QStockProduct;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.EditStockProductCommandFixture;
import be.brickbit.lpm.catering.fixture.StockProductCommandFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.service.stockproduct.command.EditStockProductCommand;
import be.brickbit.lpm.catering.service.stockproduct.command.StockProductCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StockProductControllerIT extends AbstractControllerIT {
    @Test
    public void createsStockProduct() throws Exception {
        StockProductCommand command = StockProductCommandFixture.mutable();

        performPost("/stockproduct", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(command.getName())))
                .andExpect(jsonPath("$.consumptionsLeft", is(command.getMaxConsumptions())))
                .andExpect(jsonPath("$.maxConsumptions", is(command.getMaxConsumptions())))
                .andExpect(jsonPath("$.avgConsumption", is(0)))
                .andExpect(jsonPath("$.stockLevel", is(command.getStockLevel())))
                .andExpect(jsonPath("$.clearanceType", is(command.getClearance().toString())))
                .andExpect(jsonPath("$.productType", is(command.getProductType().toString())));
    }

    @Test
    public void getsProductById() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProductJupiler();

        insert(stockProduct);

        performGet("/stockproduct/" + stockProduct.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(stockProduct.getName())))
                .andExpect(jsonPath("$.consumptionsLeft", is(stockProduct.getRemainingConsumptions())))
                .andExpect(jsonPath("$.maxConsumptions", is(stockProduct.getMaxConsumptions())))
                .andExpect(jsonPath("$.avgConsumption", is(stockProduct.getAvgConsumption())))
                .andExpect(jsonPath("$.stockLevel", is(stockProduct.getStockLevel())))
                .andExpect(jsonPath("$.clearanceType", is(stockProduct.getClearance().toString())))
                .andExpect(jsonPath("$.productType", is(stockProduct.getProductType().toString())));
    }

    @Test
    public void updatesProduct() throws Exception {
        EditStockProductCommand command = EditStockProductCommandFixture.mutable();
        StockProduct stockProduct = StockProductFixture.getStockProductJupiler();

        insert(stockProduct);

        performPut("/stockproduct/" + stockProduct.getId(), command)
                .andExpect(status().isNoContent());

        StockProduct result = new JPAQuery(getEntityManager())
                .from(QStockProduct.stockProduct)
                .where(QStockProduct.stockProduct.id.eq(stockProduct.getId()))
                .uniqueResult(QStockProduct.stockProduct);

        assertThat(result.getName()).isEqualTo(command.getName());
        assertThat(result.getClearance()).isEqualTo(command.getClearance());
        assertThat(result.getProductType()).isEqualTo(command.getProductType());
    }

    @Test
    public void deleteStockProduct() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProductJupiler();

        insert(stockProduct);

        performDelete("/stockproduct/" + stockProduct.getId())
                .andExpect(status().isNoContent());

        StockProduct result = new JPAQuery(getEntityManager())
                .from(QStockProduct.stockProduct)
                .where(QStockProduct.stockProduct.id.eq(stockProduct.getId()))
                .uniqueResult(QStockProduct.stockProduct);

        assertThat(result).isNull();
    }

    @Test
    public void getsAllStockProducts() throws Exception {
        StockProduct jupiler = StockProductFixture.getStockProductJupiler();
        StockProduct cola = StockProductFixture.getStockProductCola();
        StockProduct pizza = StockProductFixture.getStockProductPizza();
        StockProduct lasagna = StockProductFixture.getStockProductLasagna();

        insert(
                jupiler,
                cola,
                pizza,
                lasagna
        );

        performGet("/stockproduct")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void getsAllStockProductByType() throws Exception {
        StockProduct jupiler = StockProductFixture.getStockProductJupiler();
        StockProduct cola = StockProductFixture.getStockProductCola();
        StockProduct pizza = StockProductFixture.getStockProductPizza();
        StockProduct lasagna = StockProductFixture.getStockProductLasagna();

        insert(
                jupiler,
                cola,
                pizza,
                lasagna
        );

        performGet("/stockproduct?type=DRINKS")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getsAllStockProductByTypeAndClearance() throws Exception {
        StockProduct jupiler = StockProductFixture.getStockProductJupiler();
        StockProduct cola = StockProductFixture.getStockProductCola();
        StockProduct pizza = StockProductFixture.getStockProductPizza();
        StockProduct lasagna = StockProductFixture.getStockProductLasagna();

        insert(
                jupiler,
                cola,
                pizza,
                lasagna
        );

        performGet("/stockproduct?type=DRINKS&clearance=ANY")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getsAllProductTypes() throws Exception {
        performGet("/stockproduct/type")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsInAnyOrder(
                        "FOOD",
                        "SNACKS",
                        "DRINKS",
                        "OTHER"
                )));
    }

    @Test
    public void getsAllClearanceTypes() throws Exception {
        performGet("/stockproduct/clearance")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsInAnyOrder(
                        "PLUS_21",
                        "PLUS_18",
                        "PLUS_16",
                        "ANY"
                )));
    }
}