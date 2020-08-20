package com.sylvain.chat.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sylvain.chat.security.DTO.LoginRequestDTO;
import com.sylvain.chat.security.constants.SecurityConstants;
import com.sylvain.chat.security.entity.AuthUser;
import com.sylvain.chat.security.utils.JWTTokenUtils;
import com.sylvain.chat.system.exception.ErrorCode;
import com.sylvain.chat.system.exception.LoginFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * if the credentials are correct,
 * this filter create a JWT and return it in HEADER of HTTP Response
 * "Bearer " + token
 */
@Slf4j
@Deprecated
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException{
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginRequestDTO loginRequestDTO = objectMapper.readValue(request.getInputStream(), LoginRequestDTO.class);
            rememberMe.set(loginRequestDTO.isRememberMe());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e){
            log.error("Exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * if login success, create a token and return it in the HEADER of response
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AuthUser authUser = (AuthUser) authResult.getPrincipal();
        List<String> authorities = authUser.getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());
        String token = JWTTokenUtils.createToken(authUser.getUsername(),authorities,rememberMe.get());
        response.setHeader(SecurityConstants.TOKEN_HEADER,token);
    }

    @Override
    @ResponseBody
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException, LoginFailedException {
        ErrorCode errorCode = ErrorCode.CREDENTIALS_INVALID;

        if(authException instanceof DisabledException){
            errorCode = ErrorCode.ACCOUNT_DISABLED;
        }
        log.error("unsuccessfulAuthentication:" + errorCode.getMessage());

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,errorCode.getMessage());

    }


}
