package com.sylvain.chat.security.service;

import com.sylvain.chat.security.entity.AuthUser;
import com.sylvain.chat.system.entity.User;
import com.sylvain.chat.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userService.find(username);
        return new AuthUser(user);
    }
}
