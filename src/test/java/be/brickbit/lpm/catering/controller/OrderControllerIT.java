package be.brickbit.lpm.catering.controller;

import com.google.common.collect.Lists;

import com.mysema.query.jpa.impl.JPAQuery;

import org.junit.Test;

import java.math.BigDecimal;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.QOrder;
import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.util.PriceUtil;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerIT extends AbstractControllerIT {

    @Test
    public void getsAllOrders() throws Exception {
        Order order = OrderFixture.mutable();
        UserDetailsDto userDetails = UserFixture.mutable();

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        stubCore("/user/" + order.getUserId(), 200, userDetails);

        performGet("/order")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(order.getId().intValue())))
                .andExpect(jsonPath("$[0].totalPrice", is(PriceUtil.calculateTotalPrice(order).intValue())))
                .andExpect(jsonPath("$[0].timestamp", is(order.getTimestamp().format(DateUtils.getDateFormat()))))
                .andExpect(jsonPath("$[0].username", is(userDetails.getUsername())))
                .andExpect(jsonPath("$[0].seatNumber", is(userDetails.getSeatNumber())))
                .andExpect(jsonPath("$[0].status", is(OrderStatus.CREATED.toString())))
                .andExpect(jsonPath("$[0].orderLines", hasSize(2)))
                .andExpect(jsonPath("$[0].orderLines[0].id", is(order.getOrderLines().get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].orderLines[0].quantity", is(order.getOrderLines().get(0).getQuantity())))
                .andExpect(jsonPath("$[0].orderLines[0].product", is(order.getOrderLines().get(0).getProduct().getName())))
                .andExpect(jsonPath("$[0].orderLines[0].status", is(order.getOrderLines().get(0).getStatus().toString())))
                .andExpect(jsonPath("$[0].orderLines[1].id", is(order.getOrderLines().get(1).getId().intValue())))
                .andExpect(jsonPath("$[0].orderLines[1].quantity", is(order.getOrderLines().get(1).getQuantity())))
                .andExpect(jsonPath("$[0].orderLines[1].product", is(order.getOrderLines().get(1).getProduct().getName())))
                .andExpect(jsonPath("$[0].orderLines[1].status", is(order.getOrderLines().get(1).getStatus().toString())))
                .andExpect(jsonPath("$[0].orderLines", hasSize(2)))
                .andExpect(jsonPath("$[0].comment", is(order.getComment())));
    }

    @Test
    public void getsOrderHistory() throws Exception {
        Order order = OrderFixture.mutable();
        UserDetailsDto userDetails = UserFixture.mutable();
        order.setUserId(user().getId());

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        stubCore("/user/" + user().getId(), 200, userDetails);

        performGet("/order/history")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(order.getId().intValue())))
                .andExpect(jsonPath("$[0].totalPrice", is(PriceUtil.calculateTotalPrice(order).intValue())))
                .andExpect(jsonPath("$[0].timestamp", is(order.getTimestamp().format(DateUtils.getDateFormat()))))
                .andExpect(jsonPath("$[0].username", is(userDetails.getUsername())))
                .andExpect(jsonPath("$[0].seatNumber", is(userDetails.getSeatNumber())))
                .andExpect(jsonPath("$[0].status", is(OrderStatus.CREATED.toString())))
                .andExpect(jsonPath("$[0].orderLines", hasSize(2)))
                .andExpect(jsonPath("$[0].orderLines[0].id", is(order.getOrderLines().get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].orderLines[0].quantity", is(order.getOrderLines().get(0).getQuantity())))
                .andExpect(jsonPath("$[0].orderLines[0].product", is(order.getOrderLines().get(0).getProduct().getName())))
                .andExpect(jsonPath("$[0].orderLines[0].status", is(order.getOrderLines().get(0).getStatus().toString())))
                .andExpect(jsonPath("$[0].orderLines[1].id", is(order.getOrderLines().get(1).getId().intValue())))
                .andExpect(jsonPath("$[0].orderLines[1].quantity", is(order.getOrderLines().get(1).getQuantity())))
                .andExpect(jsonPath("$[0].orderLines[1].product", is(order.getOrderLines().get(1).getProduct().getName())))
                .andExpect(jsonPath("$[0].orderLines[1].status", is(order.getOrderLines().get(1).getStatus().toString())))
                .andExpect(jsonPath("$[0].orderLines", hasSize(2)))
                .andExpect(jsonPath("$[0].comment", is(order.getComment())));
    }

    @Test
    public void getsReadyOrders() throws Exception {
        Order order = OrderFixture.mutable();
        order.getOrderLines().get(0).setStatus(OrderStatus.READY);
        order.getOrderLines().get(1).setStatus(OrderStatus.READY);
        Order notReadyOrder = OrderFixture.mutable();

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order,
                notReadyOrder.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                notReadyOrder.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                notReadyOrder.getOrderLines().get(0).getProduct(),
                notReadyOrder.getOrderLines().get(1).getProduct(),
                notReadyOrder
        );

        stubCore("/user/" + order.getUserId(), 200, user());

        performGet("/order?status=READY")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(order.getId().intValue())))
                .andExpect(jsonPath("$[0].status", is(OrderStatus.READY.toString())));
    }

    @Test
    public void completesOrder() throws Exception {
        Order order = OrderFixture.mutable();
        order.getOrderLines().get(0).setStatus(OrderStatus.READY);
        order.getOrderLines().get(1).setStatus(OrderStatus.READY);

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        performPut("/order/" + order.getId() + "/process", null)
                .andExpect(status().isNoContent());

        Order resultedOrder = new JPAQuery(getEntityManager())
                .from(QOrder.order)
                .where(QOrder.order.id.eq(order.getId()))
                .uniqueResult(QOrder.order);

        assertThat(resultedOrder.getOrderLines().get(0).getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(resultedOrder.getOrderLines().get(1).getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    public void createsDirectDrinksOrder() throws Exception {
        Product product = ProductFixture.getJupiler();
        Wallet wallet = new Wallet();
        wallet.setUserId(user().getId());
        wallet.setAmount(product.getPrice());
        UserDetailsDto userDetails = UserFixture.mutable();

        stubCore("/user/seat/" + userDetails.getSeatNumber(), 200, userDetails);
        stubCore("/user/" + user().getId(), 200, user());

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId()
        );

        final String comment = "Please do not burn :)";
        DirectOrderCommand command = new DirectOrderCommand(
                user().getId(),
                Lists.newArrayList(orderLineCommand),
                comment
        );

        performPost("/order/direct", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("COMPLETED")))
                .andExpect(jsonPath("$.username", is(user().getUsername())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.comment", is(comment)));
    }

    @Test
    public void createsDirectFoodOrder() throws Exception {
        Product product = ProductFixture.getPizza();
        Wallet wallet = new Wallet();
        wallet.setUserId(user().getId());
        wallet.setAmount(product.getPrice());
        UserDetailsDto userDetails = UserFixture.mutable();

        stubCore("/user/seat/" + userDetails.getSeatNumber(), 200, userDetails);
        stubCore("/user/" + user().getId(), 200, user());

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId()
        );

        final String comment = "Please do not burn :)";
        DirectOrderCommand command = new DirectOrderCommand(
                user().getId(),
                Lists.newArrayList(orderLineCommand),
                comment
        );

        performPost("/order/direct", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("QUEUED")))
                .andExpect(jsonPath("$.username", is(user().getUsername())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.comment", is(comment)));
    }

    @Test
    public void throwsInsufficientFundsExceptionOnEmptyWallet() throws Exception {
        UserDetailsDto user = UserFixture.mutable();
        Product product = ProductFixture.getPizza();
        Wallet wallet = new Wallet();
        wallet.setUserId(user().getId());
        wallet.setAmount(BigDecimal.TEN);

        stubCore("/user/" + user.getId(), 200, user);

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId()
        );

        final String comment = "Please do not burn :)";
        DirectOrderCommand command = new DirectOrderCommand(
                user.getId(),
                Lists.newArrayList(orderLineCommand),
                comment
        );

        performPost("/order/direct", command)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.cause", is("Insufficient funds.")));
    }

    @Test
    public void createsRemoteFoodOrder() throws Exception {
        Product product = ProductFixture.getPizza();
        Wallet wallet = new Wallet();
        wallet.setUserId(user().getId());
        wallet.setAmount(product.getPrice());

        stubCore("/user/1", 200, user());

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId()
        );

        final String comment = "Please do not burn :)";
        RemoteOrderCommand command = new RemoteOrderCommand(
                Lists.newArrayList(orderLineCommand),
                comment
        );

        performPost("/order/remote", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("QUEUED")))
                .andExpect(jsonPath("$.username", is(user().getUsername())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.comment", is(comment)));
    }

    @Test
    public void createsRemoteDrinksOrder() throws Exception {
        Product product = ProductFixture.getJupiler();
        Wallet wallet = new Wallet();
        wallet.setUserId(user().getId());
        wallet.setAmount(product.getPrice());

        stubCore("/user/1", 200, user());

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId()
        );

        final String comment = "Please do not burn :)";
        RemoteOrderCommand command = new RemoteOrderCommand(
                Lists.newArrayList(orderLineCommand),
                comment
        );

        performPost("/order/remote", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("READY")))
                .andExpect(jsonPath("$.username", is(user().getUsername())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.comment", is(comment)));
    }
}