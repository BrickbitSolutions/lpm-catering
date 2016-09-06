package be.brickbit.lpm.infrastructure;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import be.brickbit.lpm.core.client.dto.UserPrincipalDto;

@Component
public class OAuthPrincipalExtractor implements PrincipalExtractor {
    @Override
    public UserPrincipalDto extractPrincipal(Map<String, Object> map) {
        return new UserPrincipalDto(
                extractLong(map, "id"),
                extractString(map, "username"),
                extractLong(map, "age"),
                extractInteger(map, "seatNumber"),
                extractBigDecimal(map, "wallet"),
                extractString(map, "mood"),
                extractString(map, "firstName"),
                extractString(map, "lastName"),
                extractString(map, "email"),
                extractList(map, "authorities")
        );
    }

    private Integer extractInteger(Map<String, Object> map, String key){
        Object value = map.get(key);
        if(value != null){
            return Integer.valueOf(value.toString());
        }else{
            return null;
        }
    }

    private Long extractLong(Map<String, Object> map, String key){
        Object value = map.get(key);
        if(value != null){
            return Long.valueOf(value.toString());
        }else{
            return null;
        }
    }

    private String extractString(Map<String, Object> map, String key){
        Object value = map.get(key);
        if(value != null){
            return value.toString();
        }else{
            return null;
        }
    }

    private BigDecimal extractBigDecimal(Map<String, Object> map, String key){
        Object value = map.get(key);
        if(value != null){
            return BigDecimal.valueOf(Double.valueOf(value.toString()));
        }else{
            return null;
        }
    }

    private List extractList(Map<String, Object> map, String key){
        Object value = map.get(key);
        if(value != null){
            return (List) value;
        }else{
            return null;
        }
    }
}
