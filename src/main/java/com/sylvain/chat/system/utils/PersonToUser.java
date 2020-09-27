package com.sylvain.chat.system.utils;

import com.sylvain.chat.system.entity.Person;
import com.sylvain.chat.system.entity.User;
import com.sylvain.chat.system.representation.UserPublicRepresentation;
import com.sylvain.chat.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class PersonToUser {
    @Autowired
    private UserService userService;

    public static PersonToUser personToUser;

    @PostConstruct
    public void init(){
        personToUser = this;
        personToUser.userService = this.userService;
    }

    public static User toUser(Person person){
        String username = person.getUsername();
        return personToUser.userService.find(username);
    }

    public static UserPublicRepresentation toUserPublicRepresentation(Person person){
        String username = person.getUsername();
        return personToUser.userService.find(username).toUserPublicRepresentation();
    }
}
