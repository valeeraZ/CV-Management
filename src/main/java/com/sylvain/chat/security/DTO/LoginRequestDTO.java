package com.sylvain.chat.security.DTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
    private boolean rememberMe;
}
