package com.sylvain.chat;

import com.sylvain.chat.system.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void validationMessages(){
        System.out.println("ErrorCode.USER_NAME_ALREADY_EXISTS.getMessage() = " + ErrorCode.USERNAME_ALREADY_EXISTS.getMessage());
    }

}
