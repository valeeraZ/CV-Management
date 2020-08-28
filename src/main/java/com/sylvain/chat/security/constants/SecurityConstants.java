package com.sylvain.chat.security.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Security Constants for JWT and URL
 */
@Configuration
public class SecurityConstants {
    /**
     * URL for login
     */
    public static String LOGIN_URL = "/login";

    /**
     * claim in JWT
     */
    public static String ROLE_CLAIMS = "role";

    /**
     * JWT expires in 60 minutes if rememberMe is false
     */
    public static long EXPIRATION = 60 * 60L;

    /**
     * JWT expires in 7 days if rememberMe is true
     */
    public static long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    /**
     * JWT signature secret key
     * for safety put it in application.properties
     */
    public static String SECRET ;

    @Value("${jwt.secret}")
    public void setSECRET(String SECRET){
        this.SECRET = SECRET;
    }

    public static String TOKEN_HEADER = "Authorization";
    public static String TOKEN_PREFIX = "Bearer ";
    public static String TOKEN_TYPE = "JWT";
}
