package be.brickbit.lpm.core.client.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPrincipalDto {
    private Long id;
    private String username;
    private String mood;
    private List<String> authorities;
}