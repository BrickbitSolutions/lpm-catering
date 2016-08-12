package be.brickbit.lpm.infrastructure;

import be.brickbit.lpm.core.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(allowCredentials = "true", origins = "*")
public abstract class AbstractController {
    protected User getCurrentUser(){
        return(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
