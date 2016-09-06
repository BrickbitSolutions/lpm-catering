package be.brickbit.lpm.core.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Arrays;
import java.util.Iterator;

import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.infrastructure.exception.ServiceException;

@Service
public class UserServiceImpl implements UserService {

    @Value("${lpm.core.url}")
    private String coreUrl;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Override
    public UserDetailsDto findOne(Long id) {
        ResponseEntity<UserDetailsDto> result = restTemplate.getForEntity(
                createUrl("/user", id),
                UserDetailsDto.class);

        if (result.getStatusCode() == HttpStatus.OK) {
            return result.getBody();
        } else {
            throw new ServiceException("User not found");
        }
    }

    @Override
    public UserDetailsDto findBySeatNumber(Integer seatNumber) {
        ResponseEntity<UserDetailsDto> result = restTemplate.getForEntity(
                createUrl("/user/seat", seatNumber),
                UserDetailsDto.class);

        if (result.getStatusCode() == HttpStatus.OK) {
            return result.getBody();
        } else {
            throw new ServiceException("User not found");
        }
    }

    private URI createUrl(Object... paths){
        StringBuilder builder = new StringBuilder();
        Iterator<Object> pathIterator = Arrays.asList(paths).iterator();

        builder.append(coreUrl);

        while (pathIterator.hasNext()){
            builder.append(pathIterator.next());
            if(pathIterator.hasNext()) {
                builder.append("/");
            }
        }

        return URI.create(builder.toString());
    }
}
