package be.brickbit.lpm.catering.service.order.mapper;

import be.brickbit.lpm.catering.domain.OrderLine;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.fixture.OrderLineCommandFixture;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderLineCommandToEntityMapperTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderLineCommandToEntityMapper mapper;

    @Test
    public void testMap() throws Exception {
        OrderLineCommand command = OrderLineCommandFixture.getOrderLineCommand();
        Product product = ProductFixture.getProduct();

        when(productRepository.getOne(command.getProductId())).thenReturn(product);

        OrderLine orderLine = mapper.map(command);

        assertThat(orderLine.getProduct()).isSameAs(product);
        assertThat(orderLine.getQuantity()).isEqualTo(command.getQuanity());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testMapFaultProduct() throws Exception {
        mapper.map(OrderLineCommandFixture.getOrderLineCommand());
    }
}