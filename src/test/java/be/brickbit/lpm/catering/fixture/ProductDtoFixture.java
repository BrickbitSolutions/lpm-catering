package be.brickbit.lpm.catering.fixture;

import com.google.common.collect.Lists;

import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.catering.domain.ProductType;
import be.brickbit.lpm.catering.controller.dto.ProductDto;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class ProductDtoFixture {
    public static ProductDto mutable(){
        return new ProductDto(
                randomLong(),
                randomString(),
                randomDecimal(),
                ProductType.DRINKS,
                ClearanceType.ANY,
                randomInt(),
                randomInt(),
                true,
                false,
                Lists.newArrayList()
        );
    }
}
