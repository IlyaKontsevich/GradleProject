package com.internship.service;

import com.internship.model.User;
import com.internship.model.UserRoleEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    private static final Logger log = Logger.getLogger(UserService.class);
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        Set<GrantedAuthority> roles = new HashSet();
        if(user.getName().equals("Admin")){
            roles.add(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.name()));
            log.info("Admin login");
        }
        else {
            roles.add(new SimpleGrantedAuthority(UserRoleEnum.ROLE_USER.name()));
            log.info("User login");
        }
        user.setAuthorities(roles);
        return user;
    }

}
