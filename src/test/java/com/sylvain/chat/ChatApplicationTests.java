package com.sylvain.chat;

import com.sylvain.chat.security.utils.JWTTokenUtils;
import com.sylvain.chat.system.enums.RoleType;
import com.sylvain.chat.system.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ChatApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testValidationMessagesInternational(){
        System.out.println("ErrorCode.USER_NAME_ALREADY_EXISTS.getMessage() = " + ErrorCode.USERNAME_ALREADY_EXISTS.getMessage());
    }
    
    @Test
    void testJWT(){
        String username = "David";
        List<String> authorities = new ArrayList<>();
        authorities.add(RoleType.USER.getName());
        boolean rememberMe = true;
        
        String token = JWTTokenUtils.createToken(username,authorities,rememberMe);
        System.out.println("token = " + token);
    }

}
