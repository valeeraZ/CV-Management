package com.sylvain.cvmanagement.system.service;

import com.google.common.collect.ImmutableMap;
import com.sylvain.cvmanagement.system.DTO.UserRegisterDTO;
import com.sylvain.cvmanagement.system.entity.Role;
import com.sylvain.cvmanagement.system.entity.User;
import com.sylvain.cvmanagement.system.entity.UserRole;
import com.sylvain.cvmanagement.system.enums.RoleType;
import com.sylvain.cvmanagement.system.exception.*;
import com.sylvain.cvmanagement.system.repository.RoleRepository;
import com.sylvain.cvmanagement.system.repository.UserRepository;
import com.sylvain.cvmanagement.system.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //private final PersonRepository personRepository;

    /**
     * register a user with role USER
     * in MySQL as an User and Neo4j as a Person
     * if exception occurs, roll back this transaction
     * @param userRegisterDTO the request from controller
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(UserRegisterDTO userRegisterDTO){
        emailNotExists(userRegisterDTO.getEmail());
        usernameNotExists(userRegisterDTO.getUsername());
        User user = userRegisterDTO.toUser();
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        userRepository.save(user);
        Role userRole = roleRepository.findByName(RoleType.USER.getName()).orElseThrow(()->new RoleNotFoundException(ImmutableMap.of("rolename",RoleType.USER.getName())));
        userRoleRepository.save(new UserRole(user,userRole));

        /*Person person = user.toPerson();
        personRepository.save(person);*/
    }

    /**
     * find an user by his username
     * might throw customized UsernameNotFoundException
     * @param username
     * @return the user object
     */
    public User find(String username){
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(ImmutableMap.of("username",username))
        );
    }

    /**
     * find an user by his email
     * @param email
     * @return the user object
     */
    public String findUsernameByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(ImmutableMap.of("email",email)))
                .getUsername();
    }

    /**
     * ensure the username does not exist in database
     * if exists, throw an exception
     * @param username
     */
    private void usernameNotExists(String username){
        boolean exist = userRepository.findByUsername(username).isPresent();
        if(exist)
            throw new UsernameAlreadyExistsException(ImmutableMap.of("username",username));
    }

    /**
     * ensure the email does not exist in database
     * if exists, throw an exception
     * @param email
     */
    private void emailNotExists(String email){
        boolean exist = userRepository.findByEmail(email).isPresent();
        if (exist)
            throw new EmailAlreadyExistsException(ImmutableMap.of("email",email));
    }
}
