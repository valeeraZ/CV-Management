package com.sylvain.cvmanagement.system.exception;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request){
        Map<String, Object> errors = new HashMap<>(8);
        ex.getConstraintViolations().forEach(error -> {
            String fieldName = ((PathImpl)error.getPropertyPath()).getLeafNode().getName();
            String message = error.getMessage();
            errors.put(fieldName,message);
        });
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_ARGUMENT_INVALID,request.getRequestURI(),errors);
        log.warn("ConstraintViolationException: " + errors.keySet());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request){
        String name = ex.getParameterName();
        String message = ex.getMessage();
        Map<String, Object> errors = ImmutableMap.of(name, message);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_ARGUMENT_INVALID,request.getRequestURI(),errors);
        log.warn("MissingServletRequestParameterException: " + errors.keySet());
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

    @ExceptionHandler(BadFormatException.class)
    public ResponseEntity<ErrorResponse> handleBadFormatException(BadFormatException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex,request.getRequestURI());
        log.warn("BadFormatException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<ErrorResponse> handleEmptyFileException(EmptyFileException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex,request.getRequestURI());
        log.warn("EmptyFileException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(EmptyKeywordException.class)
    public ResponseEntity<ErrorResponse> handleEmptyKeywordException(EmptyKeywordException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex,request.getRequestURI());
        log.warn("EmptyKeywordException: " + ex.getData());
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }



}
