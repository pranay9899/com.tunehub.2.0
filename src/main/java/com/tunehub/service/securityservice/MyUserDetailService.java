package com.tunehub.service.securityservice;

import com.tunehub.configuration.UsersPrincipal;
import com.tunehub.entity.Users;
import com.tunehub.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username);
        if(user == null){
            System.out.println("Username not found");
            throw new UsernameNotFoundException(username);
        }
        return new UsersPrincipal(user);
    }
}
