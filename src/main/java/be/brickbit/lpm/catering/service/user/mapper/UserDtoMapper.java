package be.brickbit.lpm.catering.service.user.mapper;

import be.brickbit.lpm.catering.service.user.dto.UserDto;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper implements UserMapper<UserDto> {

    @Override
    public UserDto map(User someUser) {
        return new UserDto(
                someUser.getId(),
                someUser.getUsername(),
                someUser.getLastName(),
                someUser.getFirstName(),
                someUser.getEmail()
        );
    }
}
