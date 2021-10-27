package com.sylvain.cvmanagement.system.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginFailedException extends AuthenticationException {


    public LoginFailedException(String msg) {
        super(msg);
    }
}
