package be.brickbit.lpm.catering.fixture;

import java.util.Arrays;

import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomEmail;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class UserFixture {
    public static UserDetailsDto mutable() {
        return new UserDetailsDto(
                randomLong(),
                randomString(),
                randomLong(),
                randomInt(),
                randomDecimal(),
                randomString(),
                randomString(),
                randomString(),
                randomEmail()
        );
    }

    public static UserPrincipalDto mutablePrincipal() {
        return new UserPrincipalDto(
                randomLong(),
                randomString(),
                randomLong(),
                randomInt(),
                randomDecimal(),
                randomString(),
                randomString(),
                randomString(),
                randomEmail(),
                Arrays.asList("ROLE_ADMIN", "ROLE_USER")

        );
    }

    public static UserPrincipalDto getDefaultUser() {
        return new UserPrincipalDto(
                1L,
                "admin",
                randomLong(),
                randomInt(),
                randomDecimal(),
                randomString(),
                randomString(),
                randomString(),
                randomEmail(),
                Arrays.asList("ROLE_ADMIN", "ROLE_USER")
        );
    }
}
