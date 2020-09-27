package com.sylvain.chat.security.config;

import com.sylvain.chat.security.exception.JwtAccessDeniedHandler;
import com.sylvain.chat.security.exception.JwtAuthenticationEntryPoint;
import com.sylvain.chat.security.filter.AuthorizationFilter;
import com.sylvain.chat.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService createUserDetailsService(){
        return userDetailsServiceImpl;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // disable csrf
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "api/auth/login","api/user/register").permitAll()
                // access to some resource needs authentication
                .antMatchers("/hello").permitAll()
                .antMatchers("/api/friend/**").authenticated()
                //.antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                //add custom filter
                //.addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager(), userDetailsServiceImpl))
                // no session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // handle exception during authentication/authorization
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

    }

}
