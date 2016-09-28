package be.brickbit.lpm.core.client.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPrincipalDto {
    private Long id;
    private String username;
    private Long age;
    private Integer seatNumber;
    private BigDecimal wallet;
    private String mood;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> authorities;
}