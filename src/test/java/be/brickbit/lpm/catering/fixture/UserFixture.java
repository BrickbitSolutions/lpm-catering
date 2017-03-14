package be.brickbit.lpm.catering.fixture;

import java.util.Arrays;

import be.brickbit.lpm.catering.config.LpmUserAuthenticationConverter;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class UserFixture {
    public static UserDetailsDto mutable() {
        return mutable(randomInt());
    }

    public static UserDetailsDto mutable(int age) {
        return new UserDetailsDto(
                randomLong(),
                randomString(),
                age,
                randomInt(),
                randomString()
        );
    }

    public static LpmUserAuthenticationConverter.LpmTokenPrincipal mutablePrincipal() {
        return new LpmUserAuthenticationConverter.LpmTokenPrincipal(
                randomLong(),
                randomString()
        );
    }
}
