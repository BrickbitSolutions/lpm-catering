package be.brickbit.lpm.catering.controller;

import com.google.common.collect.Lists;

import com.mysema.query.jpa.impl.JPAQuery;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.QOrder;
import be.brickbit.lpm.catering.domain.Wallet;
import be.brickbit.lpm.catering.fixture.OrderFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.service.order.command.CreateOrderCommand;
import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;
import be.brickbit.lpm.catering.service.order.util.PriceUtil;
import be.brickbit.lpm.catering.util.DateUtils;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLocalDate;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerIT extends AbstractControllerIT {

    @Test
    public void getsAllOrders() throws Exception {
        Order order = OrderFixture.mutable();

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        performGet("/order")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(order.getId().intValue())))
                .andExpect(jsonPath("$[0].totalPrice", is(PriceUtil.calculateTotalPrice(order).intValue())))
                .andExpect(jsonPath("$[0].timestamp", is(order.getTimestamp().format(DateUtils.getDateTimeFormat()))))
                .andExpect(jsonPath("$[0].userId", is(order.getUserId().intValue())))
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
    public void getsOrderById() throws Exception {
        Order order = OrderFixture.mutable();

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        performGet("/order/" + order.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(order.getId().intValue())))
                .andExpect(jsonPath("$.totalPrice", is(PriceUtil.calculateTotalPrice(order).intValue())))
                .andExpect(jsonPath("$.timestamp", is(order.getTimestamp().format(DateUtils.getDateTimeFormat()))))
                .andExpect(jsonPath("$.userId", is(order.getUserId().intValue())))
                .andExpect(jsonPath("$.status", is(OrderStatus.CREATED.toString())))
                .andExpect(jsonPath("$.orderLines", hasSize(2)))
                .andExpect(jsonPath("$.orderLines[0].id", is(order.getOrderLines().get(0).getId().intValue())))
                .andExpect(jsonPath("$.orderLines[0].quantity", is(order.getOrderLines().get(0).getQuantity())))
                .andExpect(jsonPath("$.orderLines[0].product", is(order.getOrderLines().get(0).getProduct().getName())))
                .andExpect(jsonPath("$.orderLines[0].status", is(order.getOrderLines().get(0).getStatus().toString())))
                .andExpect(jsonPath("$.orderLines[1].id", is(order.getOrderLines().get(1).getId().intValue())))
                .andExpect(jsonPath("$.orderLines[1].quantity", is(order.getOrderLines().get(1).getQuantity())))
                .andExpect(jsonPath("$.orderLines[1].product", is(order.getOrderLines().get(1).getProduct().getName())))
                .andExpect(jsonPath("$.orderLines[1].status", is(order.getOrderLines().get(1).getStatus().toString())))
                .andExpect(jsonPath("$.orderLines", hasSize(2)))
                .andExpect(jsonPath("$.comment", is(order.getComment())));
    }

    @Test
    public void getsOrderHistory() throws Exception {
        Order order = OrderFixture.mutable();

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        performGet("/order/history")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(order.getId().intValue())))
                .andExpect(jsonPath("$[0].totalPrice", is(PriceUtil.calculateTotalPrice(order).intValue())))
                .andExpect(jsonPath("$[0].timestamp", is(order.getTimestamp().format(DateUtils.getDateTimeFormat()))))
                .andExpect(jsonPath("$[0].userId", is(order.getUserId().intValue())))
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
    public void handlesReservation() throws Exception {
        Order order = OrderFixture.mutable();
        order.setHoldUntil(LocalDate.now());
        order.getOrderLines().get(0).setStatus(OrderStatus.CREATED);
        order.getOrderLines().get(1).setStatus(OrderStatus.CREATED);

        insert(
                order.getOrderLines().get(0).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(1).getProduct().getReceipt().get(0).getStockProduct(),
                order.getOrderLines().get(0).getProduct(),
                order.getOrderLines().get(1).getProduct(),
                order
        );

        performPut("/order/" + order.getId() + "/process/reservation", null)
                .andExpect(status().isNoContent());

        Order resultedOrder = new JPAQuery(getEntityManager())
                .from(QOrder.order)
                .where(QOrder.order.id.eq(order.getId()))
                .uniqueResult(QOrder.order);

        assertThat(resultedOrder.getOrderLines().get(0).getStatus()).isEqualTo(OrderStatus.READY);
        assertThat(resultedOrder.getOrderLines().get(1).getStatus()).isEqualTo(OrderStatus.QUEUED);
    }

    @Test
    public void createsDirectReservation() throws Exception {
        Product product = ProductFixture.getJupiler();
        Wallet wallet = new Wallet();
        wallet.setUserId(userDetails().getId());
        wallet.setAmount(product.getPrice());
        UserDetailsDto userDetails = UserFixture.mutable();

        stubCore("/user/seat/" + userDetails.getSeatNumber(), 200, userDetails);
        stubCore("/user/" + userDetails().getId(), 200, userDetails());

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId(),
                Lists.newArrayList()
        );

        CreateOrderCommand command = new CreateOrderCommand(
                userDetails().getId(),
                Lists.newArrayList(orderLineCommand),
                randomString(),
                randomLocalDate()
        );

        performPost("/order", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("CREATED")))
                .andExpect(jsonPath("$.userId", is(userDetails().getId().intValue())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)));
    }

    @Test
    public void createsDirectDrinksOrder() throws Exception {
        Product product = ProductFixture.getJupiler();
        UserDetailsDto userDetails = UserFixture.mutable();
        Wallet wallet = new Wallet();
        wallet.setUserId(userDetails.getId());
        wallet.setAmount(product.getPrice());

        stubCore("/user/" + userDetails.getId(), 200, userDetails);

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId(),
                Lists.newArrayList()
        );

        final String comment = "Please do not burn :)";
        CreateOrderCommand command = new CreateOrderCommand(
                userDetails.getId(),
                Lists.newArrayList(orderLineCommand),
                comment,
                null
        );

        performPost("/order", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("COMPLETED")))
                .andExpect(jsonPath("$.userId", is(userDetails.getId())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.comment", is(comment)));
    }

    @Test
    public void createsDirectFoodOrder() throws Exception {
        Product product = ProductFixture.getPizza();
        Wallet wallet = new Wallet();
        wallet.setUserId(userDetails().getId());
        wallet.setAmount(product.getPrice());
        UserDetailsDto userDetails = UserFixture.mutable();

        stubCore("/user/seat/" + userDetails.getSeatNumber(), 200, userDetails);
        stubCore("/user/" + userDetails().getId(), 200, userDetails());

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product.getSupplements().get(0),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId(),
                Lists.newArrayList(product.getSupplements().get(0).getId())
        );

        final String comment = "Please do not burn :)";
        CreateOrderCommand command = new CreateOrderCommand(
                userDetails().getId(),
                Lists.newArrayList(orderLineCommand),
                comment,
                null
        );

        performPost("/order", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("QUEUED")))
                .andExpect(jsonPath("$.userId", is(userDetails().getId().intValue())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.comment", is(comment)));
    }

    @Test
    public void throwsInsufficientFundsExceptionOnEmptyWallet() throws Exception {
        UserDetailsDto user = UserFixture.mutable();
        Product product = ProductFixture.getPizza();
        Wallet wallet = new Wallet();
        wallet.setUserId(userDetails().getId());
        wallet.setAmount(BigDecimal.TEN);

        stubCore("/user/" + user.getId(), 200, user);

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId(),
                Lists.newArrayList()
        );

        final String comment = "Please do not burn :)";
        CreateOrderCommand command = new CreateOrderCommand(
                user.getId(),
                Lists.newArrayList(orderLineCommand),
                comment,
                null
        );

        performPost("/order", command)
                .andExpect(status().isConflict());
    }

    @Test
    public void createsRemoteReservation() throws Exception {
        Product product = ProductFixture.getPizza();
        Wallet wallet = new Wallet();
        wallet.setUserId(userDetails().getId());
        wallet.setAmount(product.getPrice());

        stubCore("/user/1", 200, userDetails());

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product.getSupplements().get(0),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId(),
                Lists.newArrayList(product.getSupplements().get(0).getId())
        );

        CreateOrderCommand command = new CreateOrderCommand(
                null,
                Lists.newArrayList(orderLineCommand),
                randomString(),
                randomLocalDate()
        );

        performPost("/order", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("CREATED")))
                .andExpect(jsonPath("$.userId", is(userDetails().getId().intValue())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)));
    }

    @Test
    public void createsRemoteFoodOrder() throws Exception {
        Product product = ProductFixture.getPizza();
        Wallet wallet = new Wallet();
        wallet.setUserId(userDetails().getId());
        wallet.setAmount(product.getPrice());

        stubCore("/user/1", 200, userDetails());

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product.getSupplements().get(0),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId(),
                Lists.newArrayList(product.getSupplements().get(0).getId())
        );

        final String comment = "Please do not burn :)";
        CreateOrderCommand command = new CreateOrderCommand(
                null,
                Lists.newArrayList(orderLineCommand),
                comment,
                null
        );

        performPost("/order", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("QUEUED")))
                .andExpect(jsonPath("$.userId", is(userDetails().getId().intValue())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.comment", is(comment)));
    }

    @Test
    public void createsRemoteDrinksOrder() throws Exception {
        Product product = ProductFixture.getJupiler();
        Wallet wallet = new Wallet();
        wallet.setUserId(userDetails().getId());
        wallet.setAmount(product.getPrice());

        stubCore("/user/1", 200, userDetails());

        insert(
                product.getReceipt().get(0).getStockProduct(),
                product,
                wallet
        );

        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                product.getId(),
                Lists.newArrayList()
        );

        final String comment = "Please do not burn :)";
        CreateOrderCommand command = new CreateOrderCommand(
                null,
                Lists.newArrayList(orderLineCommand),
                comment,
                null
        );

        performPost("/order", command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("READY")))
                .andExpect(jsonPath("$.userId", is(userDetails().getId().intValue())))
                .andExpect(jsonPath("$.orderLines", hasSize(1)))
                .andExpect(jsonPath("$.comment", is(comment)));
    }

    @Test
    public void refusesRemoteOrderHoldUntilNotInFuture() throws Exception {
        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                randomLong(),
                Lists.newArrayList()
        );

        CreateOrderCommand command = new CreateOrderCommand(
                null,
                Lists.newArrayList(orderLineCommand),
                randomString(),
                LocalDate.now()
        );

        performPost("/order", command)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void refusesDirectOrderHoldUntilNotInFuture() throws Exception {
        OrderLineCommand orderLineCommand = new OrderLineCommand(
                1,
                randomLong(),
                Lists.newArrayList()
        );

        CreateOrderCommand command = new CreateOrderCommand(
                randomLong(),
                Lists.newArrayList(orderLineCommand),
                randomString(),
                LocalDate.now()
        );

        performPost("/order", command)
                .andExpect(status().isBadRequest());
    }
}