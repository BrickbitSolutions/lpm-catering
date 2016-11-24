package be.brickbit.lpm.core.client;

import be.brickbit.lpm.core.client.dto.UserDetailsDto;

public interface UserService {
    UserDetailsDto findOne(Long id);
    UserDetailsDto findBySeatNumber(Integer seatNumber);
    void notify(Long userId, String message);
}