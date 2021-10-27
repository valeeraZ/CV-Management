package com.sylvain.cvmanagement.security.DTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String usernameOrEmail;
    private String password;
    private boolean rememberMe;
}
