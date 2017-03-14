package be.brickbit.lpm.catering.controller;

import com.google.common.collect.Lists;

import org.junit.Test;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.domain.StockFlowDetail;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockFlowCommandFixture;
import be.brickbit.lpm.catering.fixture.StockFlowFixture;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.stockflow.command.StockFlowCommand;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StockFlowControllerIT extends AbstractControllerIT {
    @Test
    public void getsStockFlows() throws Exception {
        StockProduct pizza = StockProductFixture.getStockProductPizza();
        StockFlow stockFlow = StockFlowFixture.getStockFlow();
        StockFlowDetail stockFlowDetail = new StockFlowDetail();
        stockFlowDetail.setStockProduct(pizza);
        stockFlowDetail.setQuantity(1);
        stockFlow.setDetails(Lists.newArrayList(stockFlowDetail));
        UserDetailsDto user = UserFixture.mutable();

        stubCore("/user/1", 200, user);

        insert(
                pizza,
                stockFlow,
                stockFlowDetail
        );

        performGet("/stock/flow")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(stockFlow.getId().intValue())))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].type", is(stockFlow.getStockFlowType().toString())))
                .andExpect(jsonPath("$[0].level", is(stockFlow.getLevel().toString())))
                .andExpect(jsonPath("$[0].timestamp", is(stockFlow.getTimestamp().format(DateUtils.getDateTimeFormat()))))
                .andExpect(jsonPath("$[0].stockFlowDetails", hasSize(1)))
                .andExpect(jsonPath("$[0].stockFlowDetails[0].productName", is(pizza.getName())))
                .andExpect(jsonPath("$[0].stockFlowDetails[0].quantity", is(stockFlowDetail.getQuantity())));
    }

    @Test
    public void savesStockFlow() throws Exception {
        StockProduct pizza = StockProductFixture.getStockProductPizza();

        insert(
                pizza
        );

        stubCore("/user/1", 200, userPrincipal());

        StockFlowCommand command = StockFlowCommandFixture.mutable();
        command.setProductId(pizza.getId());

        performPost("/stock/flow", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is(userPrincipal().getUsername())))
                .andExpect(jsonPath("$.type", is(command.getStockFlowType().toString())))
                .andExpect(jsonPath("$.level", is(command.getLevel().toString())))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.stockFlowDetails", hasSize(1)))
                .andExpect(jsonPath("$.stockFlowDetails[0].productName", is(pizza.getName())))
                .andExpect(jsonPath("$.stockFlowDetails[0].quantity", is(command.getQuantity())));
    }

    @Test
    public void getsStockFlowTypes() throws Exception {
        performGet("/stock/flow/type")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", containsInAnyOrder(
                        "SOLD",
                        "PURCHASED",
                        "CORRECTION",
                        "RETURNED",
                        "LOSS"
                )));
    }
}