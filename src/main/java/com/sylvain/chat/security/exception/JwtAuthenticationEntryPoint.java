package com.sylvain.chat.security.exception;

import com.sylvain.chat.security.utils.JsonErrorHandler;
import com.sylvain.chat.system.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * The access needs authentication or authentication expires(invalid token)
     * send 401 error in the response
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.LOGIN_PREREQUISITE;
        //normally we only have 3 cases: bad credentials, account being disabled or login prerequisite
        if(authException instanceof BadCredentialsException){
            errorCode = ErrorCode.CREDENTIALS_INVALID;
        }
        if(authException instanceof DisabledException){
            errorCode = ErrorCode.ACCOUNT_DISABLED;
        }
        if(authException instanceof InsufficientAuthenticationException){
            errorCode = ErrorCode.LOGIN_PREREQUISITE;
        }
        log.warn(authException.getMessage() +", Resource URI: " + request.getRequestURI() +", from IP: " + request.getRemoteAddr());
        JsonErrorHandler.printJson(errorCode,request,response);
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED,errorCode.getMessage());
    }
}
