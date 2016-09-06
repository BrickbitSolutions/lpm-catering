package be.brickbit.lpm.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.infrastructure.AbstractController;

@RestController
public class HomeController extends AbstractController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getHome() {
        return "{\"status\":\"UP\"}";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto test() {
        return userService.findOne(1L);
    }
}
