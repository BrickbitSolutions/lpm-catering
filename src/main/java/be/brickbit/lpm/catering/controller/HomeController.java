package be.brickbit.lpm.catering.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.brickbit.lpm.infrastructure.AbstractController;

@RestController
public class HomeController extends AbstractController {
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getHome() {
        return "{\"status\":\"UP\"}";
    }
}
