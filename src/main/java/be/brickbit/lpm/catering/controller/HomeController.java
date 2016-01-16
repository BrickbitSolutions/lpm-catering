package be.brickbit.lpm.catering.controller;

import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.AbstractController;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class HomeController extends AbstractController{

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getHome(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "{\"message\":\"Hello " + user.getUsername() + "\"}";
    }
}
