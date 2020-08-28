package com.sylvain.chat.security.controller;

import com.sylvain.chat.security.DTO.LoginRequestDTO;
import com.sylvain.chat.security.constants.SecurityConstants;
import com.sylvain.chat.security.entity.AuthUser;
import com.sylvain.chat.security.utils.CurrentUserUtils;
import com.sylvain.chat.security.utils.JWTTokenUtils;
import com.sylvain.chat.system.entity.User;
import com.sylvain.chat.system.representation.UserPrivateRepresentation;
import com.sylvain.chat.system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class AuthController {

    private final ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
    private final AuthenticationManager authenticationManager;
    private final CurrentUserUtils currentUserUtils;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserPrivateRepresentation> authenticateUser(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        rememberMe.set(loginRequestDTO.isRememberMe());

        String usernameOrEmail = loginRequestDTO.getUsernameOrEmail();
        //check it's an email address or a sequence of character presenting username
        if(EmailValidator.getInstance().isValid(usernameOrEmail)){
            //get the correspondent username
            //possible to throw EmailNotFoundException
            usernameOrEmail = userService.findUsernameByEmail(usernameOrEmail);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usernameOrEmail, loginRequestDTO.getPassword());
        log.info("User tries to authenticate: " + usernameOrEmail);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //login success
        //1. create token
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        List<String> authorities = authUser.getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());
        String token = JWTTokenUtils.createToken(authUser.getUsername(),authorities,rememberMe.get());

        //2. get information(UserPrivateRepresentation) about user
        User currentUser = currentUserUtils.getCurrentUser();
        UserPrivateRepresentation representation = currentUser.toUserPrivateRepresentation();

        return ResponseEntity.ok().header(SecurityConstants.TOKEN_HEADER,token).
                body(representation);
    }
}
