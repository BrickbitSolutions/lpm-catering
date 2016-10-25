package be.brickbit.lpm.core.client.dto;

import lombok.Value;

@Value
public class UserDetailsDto {
    private Long id;
    private String username;
    private Long age;
    private Integer seatNumber;
    private String mood;
}