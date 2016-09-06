package be.brickbit.lpm.infrastructure;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;

import be.brickbit.lpm.core.client.dto.UserPrincipalDto;

@CrossOrigin(allowCredentials = "true", origins = "*")
public abstract class AbstractController {
    protected UserPrincipalDto getCurrentUser() {
        return (UserPrincipalDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
