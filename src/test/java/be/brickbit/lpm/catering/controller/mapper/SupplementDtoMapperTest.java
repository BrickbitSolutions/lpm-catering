package be.brickbit.lpm.catering.controller.mapper;

import org.junit.Before;
import org.junit.Test;

import be.brickbit.lpm.catering.controller.mapper.SupplementDtoMapper;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.fixture.StockProductFixture;
import be.brickbit.lpm.catering.controller.dto.SupplementDto;

import static org.assertj.core.api.Assertions.assertThat;

public class SupplementDtoMapperTest {
    private SupplementDtoMapper supplementDtoMapper;

    @Before
    public void setUp() throws Exception {
        supplementDtoMapper = new SupplementDtoMapper();
    }

    @Test
    public void map() throws Exception {
        StockProduct stockProduct = StockProductFixture.getStockProductCola();

        SupplementDto supplementDto = supplementDtoMapper.map(stockProduct);

        assertThat(supplementDto.getId()).isEqualTo(stockProduct.getId());
        assertThat(supplementDto.getName()).isEqualTo(stockProduct.getName());
    }
}