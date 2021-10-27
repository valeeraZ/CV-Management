package com.sylvain.cvmanagement.security.utils;

import com.sylvain.cvmanagement.system.entity.User;
import com.sylvain.cvmanagement.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * get the user's authentication from context
 * to recognize who is accessing to the api
 */
@Component
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class CurrentUserUtils {
    private final UserService userService;

    public User getCurrentUser(){
        return userService.find(getCurrentUsername());
    }

    public static String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() != null){
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
