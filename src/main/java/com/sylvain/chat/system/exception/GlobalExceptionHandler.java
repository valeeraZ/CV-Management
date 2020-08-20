package com.sylvain.chat.system.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("BaseException: " + errorResponse.toString());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String, Object> errors = new HashMap<>(8);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName,message);
        });
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_ARGUMENT_INVALID,request.getRequestURI(),errors);
        log.error("MethodArgumentNotValidException: " + errorResponse.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex,request.getRequestURI());
        log.error("UsernameAlreadyExistsException: " + errorResponse.toString());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("RoleNotFoundException: " + errorResponse.toString());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @Deprecated
    @ExceptionHandler(UsernameNotExistedException.class)
    public ResponseEntity<ErrorResponse> handleUserNotExistedException(UsernameNotExistedException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("UserNotFoundException: " + errorResponse.toString());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    /*@ExceptionHandler({BadCredentialsException.class,DisabledException.class,UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request){
        Map<String, Object> errors = new HashMap<>(8);
        String message = ex.getMessage();
        errors.put("message",message);
        //whether wrong password or wrong username, we always return "bad credentials" message
        ErrorCode errorCode = ErrorCode.CREDENTIALS_INVALID;

        if(ex instanceof DisabledException){
            errorCode = ErrorCode.ACCOUNT_DISABLED;
        }
        ErrorResponse errorResponse = new ErrorResponse(errorCode,request.getRequestURI(),errors);
        log.error("AuthenticationException: " + ex.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }*/

    /*@ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorResponse> handleLoginFailedException(LoginFailedException ex,HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("LoginFailedException: " + errorResponse.toString());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }*/


}
