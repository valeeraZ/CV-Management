package com.sylvain.chat.system.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        log.warn("MethodArgumentNotValidException: " + errors.keySet());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex,request.getRequestURI());
        log.warn("UsernameAlreadyExistsException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex,request.getRequestURI());
        log.warn("EmailAlreadyExistsException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("RoleNotFoundException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.warn("EmailNotFoundException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    /**
     * handle my customized Username not found exception (not the one in Spring Security)
     * this is an exception should never happen
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("UsernameNotFoundException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    /**
     * This is a database neo4j-mysql sync exception which should not happen
     */
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("PersonNotFoundException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(FriendshipAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleFriendshipAlreadyExistsException(FriendshipAlreadyExistsException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.warn("FriendshipAlreadyExistsException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    /**
     * This is an exception which should never happen
     * Usually when a request is deleted by direct db operation, not by the client front end
     */
    @ExceptionHandler(FriendRequestIdNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleFriendRequestIdNotExistsException(FriendRequestIdNotExistsException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex, request.getRequestURI());
        log.error("FriendRequestNotExistsException: " + ex.getData());
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
