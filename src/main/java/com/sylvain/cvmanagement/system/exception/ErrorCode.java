package com.sylvain.cvmanagement.system.exception;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

@Getter
public enum ErrorCode {

    USERNAME_ALREADY_EXISTS(1001, HttpStatus.BAD_REQUEST, "username.exists"),
    EMAIL_ALREADY_EXISTS(1001,HttpStatus.BAD_REQUEST,"email.exists"),
    ROLE_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "role.notfound"),
    EMAIL_NOT_FOUND(1002,HttpStatus.NOT_FOUND,"email.notfound"),
    USERNAME_NOT_FOUND(1002,HttpStatus.NOT_FOUND,"username.notfound"),
    VERIFY_JWT_FAILED(1003, HttpStatus.UNAUTHORIZED,"token.failed"),
    METHOD_ARGUMENT_INVALID(1004,HttpStatus.BAD_REQUEST,"argument.failed"),
    CREDENTIALS_INVALID(1003,HttpStatus.UNAUTHORIZED,"credentials.invalid"),
    ACCOUNT_DISABLED(1003,HttpStatus.UNAUTHORIZED,"account.disabled"),
    LOGIN_PREREQUISITE(1003,HttpStatus.UNAUTHORIZED,"login.prerequisite"),
    ACCESS_DENIED(1003,HttpStatus.FORBIDDEN,"access.denied"),
    BAD_FILE_EXTENSION(1004, HttpStatus.BAD_REQUEST, "file.extension"),
    EMPTY_FILE(1004, HttpStatus.BAD_REQUEST, "file.empty"),
    EMPTY_KEYWORD(1004, HttpStatus.BAD_REQUEST,"keyword.empty");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    private MessageSource messageSource;

    public ErrorCode setMessageSource(MessageSource messagesSource){
        this.messageSource = messagesSource;
        return this;
    }

    //inject bean by static inside class
    //and set the value into enum
    @Component
    public static class ReportTypeServiceInjector {

        @Autowired
        private MessageSource messageSource;

        @PostConstruct
        public void postConstruct() {
            for (ErrorCode errorCode : EnumSet.allOf(ErrorCode.class))
                errorCode.setMessageSource(messageSource);
        }
    }

    public String getMessage() {
        return messageSource.getMessage(message,null,message, LocaleContextHolder.getLocale());
    }
}
