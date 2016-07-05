package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.core.domain.Authority;
import be.brickbit.lpm.core.domain.User;

import java.util.Collections;
import java.util.HashSet;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomEmail;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLocalDate;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;

public class UserFixture {
    public static User getCateringAdmin(){
            User user = new User();

            user.setSeatNumber(randomInt());
            user.setEmail(randomEmail());
            user.setUsername(randomString());
            user.setPassword(randomString());
            user.setFirstName(randomString());
            user.setLastName(randomString());
            user.setBirthDate(randomLocalDate());
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            user.setMood(randomString(50));
            user.setWallet(randomDecimal());
            user.setAuthorities(Collections.singleton(new Authority("ROLE_CATERING_ADMIN")));

            return user;
    }
}
