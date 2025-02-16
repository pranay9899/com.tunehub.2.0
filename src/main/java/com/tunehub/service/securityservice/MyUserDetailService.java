package com.tunehub.service.securityservice;

import com.tunehub.configuration.UsersPrincipal;
import com.tunehub.entity.Role;
import com.tunehub.entity.Users;
import com.tunehub.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username); // Will throw UsernameNotFoundException if not found

        Role role = user.getRole();
        List<GrantedAuthority>  grantedAuthorities = new ArrayList<>();

        if(role != null){
            String roleType = role.name();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roleType));
        } else {
            System.out.println("WARNING: User '" + username + "' has NO ROLE assigned in database.");
        }

        System.out.println("Loaded user: "+username+" "+grantedAuthorities);
        return new UsersPrincipal(user, grantedAuthorities);
    }
}