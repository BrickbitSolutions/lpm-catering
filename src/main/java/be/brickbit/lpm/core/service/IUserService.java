package be.brickbit.lpm.core.service;

import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService, Service<User> {
}