package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.product.dto.SupplementDto;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class SupplementDtoFixture {
    public static SupplementDto mutable(){
        return new SupplementDto(
                randomLong(),
                randomString()
        );
    }
}
