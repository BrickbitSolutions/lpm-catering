package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.queue.dto.QueueDto;

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
                randomString()
        );
    }
}
