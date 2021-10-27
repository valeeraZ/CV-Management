package com.sylvain.cvmanagement;

import com.sylvain.cvmanagement.security.constants.SecurityConstants;
import com.sylvain.cvmanagement.security.utils.JWTTokenUtils;
import com.sylvain.cvmanagement.system.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CVManagementApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testJWT(){
        String username = "David";
        List<String> authorities = new ArrayList<>();
        authorities.add(RoleType.USER.getName());
        boolean rememberMe = true;
        
        String token = JWTTokenUtils.createToken(username,authorities,rememberMe).replace(SecurityConstants.TOKEN_PREFIX,"");;
        assert(JWTTokenUtils.getUsernameByToken(token).equals(username));
    }
}
