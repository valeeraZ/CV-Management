package com.sylvain.chat.security.service;

import com.sylvain.chat.security.entity.AuthUser;
import com.sylvain.chat.system.entity.User;
import com.sylvain.chat.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userService.find(username);
        return new AuthUser(user);
    }
}
