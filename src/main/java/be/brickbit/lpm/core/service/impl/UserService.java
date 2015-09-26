package be.brickbit.lpm.core.service.impl;

import be.brickbit.lpm.core.repository.UserRepository;
import be.brickbit.lpm.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(value = "userTransactionManager", readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(this::getUserNotFoundException);
    }

    private UsernameNotFoundException getUserNotFoundException(){
        throw new UsernameNotFoundException("User not found");
    }
}
