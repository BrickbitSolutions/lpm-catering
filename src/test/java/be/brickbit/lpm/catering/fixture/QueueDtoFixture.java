package be.brickbit.lpm.catering.fixture;

import com.google.common.collect.Lists;

import be.brickbit.lpm.catering.controller.dto.QueueDto;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLocalDateTime;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class QueueDtoFixture {
    public static QueueDto mutable() {
        return new QueueDto(
                randomLong(),
                randomString(),
                randomLocalDateTime(),
                randomInt(),
                randomLong(),
                randomString(),
                randomString(),
                randomString(),
                Lists.newArrayList(randomString())
        );
    }
}
