package be.brickbit.lpm.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

import be.brickbit.lpm.catering.controller.dto.UserClearanceDto;
import be.brickbit.lpm.catering.domain.ClearanceType;
import be.brickbit.lpm.core.client.UserService;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.infrastructure.AbstractController;

@RequestMapping(value = "/user/clearance")
@RestController
public class ClearanceController extends AbstractController {
    private UserService userService;

    @Autowired
    public ClearanceController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserClearanceDto getUserClearances() {
        UserDetailsDto userDetails = userService.findOne(getCurrentUser().getId());

        return new UserClearanceDto(
                Arrays.stream(ClearanceType.values())
                        .filter(c -> c.getClearanceLevel() <= userDetails.getAge())
                        .collect(Collectors.toList())
        );
    }
}
