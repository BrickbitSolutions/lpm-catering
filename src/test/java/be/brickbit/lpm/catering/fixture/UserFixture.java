package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.core.domain.Authority;
import be.brickbit.lpm.core.domain.User;

import java.util.Collections;

public class UserFixture {
    public static User getCateringAdmin(){
        return new User("jay", "pwd", "Jonas", "Liekens", "soulscammer@gmail.com", Collections.singleton(new Authority("ROLE_CATERING_ADMIN")));
    }
}
