package be.brickbit.lpm.catering.fixture;

import be.brickbit.lpm.catering.service.user.dto.UserDto;

public class UserDtoFixture {
    public static UserDto getUserDto(){
        return new UserDto(
                1L,
                "jay",
                "Liekens",
                "Jonas",
                "jonas.liekens@brickbit.be"
        );
    }
}
