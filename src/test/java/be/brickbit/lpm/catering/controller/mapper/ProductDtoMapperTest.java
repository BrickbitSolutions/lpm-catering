package be.brickbit.lpm.catering.controller.mapper;

import be.brickbit.lpm.catering.controller.mapper.ProductDtoMapper;
import be.brickbit.lpm.catering.controller.mapper.SupplementDtoMapper;
import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.ProductFixture;
import be.brickbit.lpm.catering.fixture.SupplementDtoFixture;
import be.brickbit.lpm.catering.controller.dto.ProductDto;
import be.brickbit.lpm.catering.controller.dto.SupplementDto;
import be.brickbit.lpm.catering.service.stockflow.util.StockFlowUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDtoMapperTest {

    @Mock
    private SupplementDtoMapper supplementDtoMapper;

    @InjectMocks
    private ProductDtoMapper mapper;

    @Test
    public void testMap() throws Exception {
        Product product = ProductFixture.getPizza();
        SupplementDto supplementDto = SupplementDtoFixture.mutable();

        when(supplementDtoMapper.map(any(StockProduct.class))).thenReturn(supplementDto);

        ProductDto productDto = mapper.map(product);

        assertThat(productDto.getClearanceType()).isEqualTo(product.getClearance());
        assertThat(productDto.getName()).isEqualTo(product.getName());
        assertThat(productDto.getProductType()).isEqualTo(product.getProductType());
        assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
        assertThat(productDto.getStockLevel()).isEqualTo(
                StockFlowUtil.calculateCurrentStockLevel(product.getReceipt().get(0).getStockProduct()) / product.getReceipt().get(0).getQuantity()
        );
        assertThat(productDto.getAvgConsumption()).isEqualTo(product.getAvgConsumption());
        assertThat(productDto.getAvailable()).isEqualTo(product.getAvailable());
        assertThat(productDto.getReservationOnly()).isEqualTo(product.getReservationOnly());
        assertThat(productDto.getSupplements()).containsExactly(supplementDto);
    }
}