package com.sylvain.cvmanagement.security.entity;

import com.sylvain.cvmanagement.system.entity.User;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@ToString
public class AuthUser implements UserDetails {

    private String id;
    private String username;
    private String password;
    private Boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public AuthUser(){}

    public AuthUser(User user){
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        enabled = user.getEnabled() == null ? true : user.getEnabled();
        authorities = user.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
