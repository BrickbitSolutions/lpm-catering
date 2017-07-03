package be.brickbit.lpm.catering.controller.mapper;

import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.controller.dto.StockProductDto;
import be.brickbit.lpm.catering.controller.dto.StockProductDtoMapper;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StockProductDtoMapperTest {

    private StockProductDtoMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new StockProductDtoMapper();
    }

    @Test
    public void testMap() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProductJupiler();
        StockProductDto dto = mapper.map(stockProduct);

        assertThat(dto.getId()).isEqualTo(stockProduct.getId());
        assertThat(dto.getName()).isEqualTo(stockProduct.getName());
        assertThat(dto.getClearanceType()).isEqualTo(stockProduct.getClearance());
        assertThat(dto.getConsumptionsLeft()).isEqualTo(stockProduct.getRemainingConsumptions());
        assertThat(dto.getMaxConsumptions()).isEqualTo(stockProduct.getMaxConsumptions());
        assertThat(dto.getAvgConsumption()).isEqualTo(stockProduct.getAvgConsumption());
        assertThat(dto.getProductType()).isEqualTo(stockProduct.getProductType());
        assertThat(dto.getStockLevel()).isEqualTo(stockProduct.getStockLevel());
    }
}