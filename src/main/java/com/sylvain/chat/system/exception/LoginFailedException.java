package com.sylvain.chat.system.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public class LoginFailedException extends AuthenticationException {


    public LoginFailedException(String msg) {
        super(msg);
    }
}
