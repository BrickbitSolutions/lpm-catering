package be.brickbit.lpm.catering.fixture;

import java.util.Arrays;

import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;

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
                randomString()
        );
    }

    public static UserPrincipalDto mutablePrincipal() {
        return new UserPrincipalDto(
                randomLong(),
                randomString(),
                randomString(),
                Arrays.asList("ROLE_ADMIN", "ROLE_USER")
        );
    }

    public static UserPrincipalDto getDefaultUser() {
        return new UserPrincipalDto(
                1L,
                "admin",
                randomString(),
                Arrays.asList("ROLE_ADMIN", "ROLE_USER")
        );
    }
}
