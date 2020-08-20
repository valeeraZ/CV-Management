package com.sylvain.chat.security.filter;

import com.sylvain.chat.security.constants.SecurityConstants;
import com.sylvain.chat.security.service.UserDetailsServiceImpl;
import com.sylvain.chat.security.utils.JWTTokenUtils;
import com.sylvain.chat.system.exception.UsernameNotExistedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsServiceImpl userDetailsService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authorization){
        log.info("get authentication");
        String token = authorization.replace(SecurityConstants.TOKEN_PREFIX,"");
        try{
            String username = JWTTokenUtils.getUsernameByToken(token);
            log.info("check username: " + username);
            if(!StringUtils.isEmpty(username)){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(username,null, userDetails.getAuthorities());
                return userDetails.isEnabled() ? usernamePasswordAuthenticationToken : null;
            }
        }catch (UsernameNotFoundException | SignatureException | ExpiredJwtException | MalformedJwtException | IllegalArgumentException e){
            log.warn("JWT is invalid. Detail:" + e.getMessage());
        }

        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if(token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)){
            SecurityContextHolder.clearContext();
        }else{
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request,response);
    }
}
