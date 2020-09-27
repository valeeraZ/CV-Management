package com.sylvain.chat.security.filter;

import com.sylvain.chat.security.constants.SecurityConstants;
import com.sylvain.chat.security.service.UserDetailsServiceImpl;
import com.sylvain.chat.security.utils.JWTTokenUtils;
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

    private UsernamePasswordAuthenticationToken getAuthentication(String authorization){
        //log.info("get authentication");
        String token = authorization.replace(SecurityConstants.TOKEN_PREFIX,"");
        String username = null;
        try{
            //log.info("check username: " + username);
            username = JWTTokenUtils.getUsernameByToken(token);
            if(!StringUtils.isEmpty(username)){
                //log.info("User tries to authenticate: " + username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(username,null, userDetails.getAuthorities());
                return userDetails.isEnabled() ? usernamePasswordAuthenticationToken : null;
            }
        }catch (UsernameNotFoundException | SignatureException | ExpiredJwtException | MalformedJwtException | IllegalArgumentException e){
            log.warn("Authentication failed: User " + username +"'s JWT is invalid. Detail: " + e.getMessage());
        }

        return null;
    }
}
