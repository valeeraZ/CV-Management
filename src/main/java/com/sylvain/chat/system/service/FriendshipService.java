package com.sylvain.chat.system.service;


import com.google.common.collect.ImmutableMap;
import com.sylvain.chat.security.utils.CurrentUserUtils;
import com.sylvain.chat.system.entity.Friendship;
import com.sylvain.chat.system.entity.Person;
import com.sylvain.chat.system.exception.FriendshipAlreadyExistsException;
import com.sylvain.chat.system.exception.PersonNotFoundException;
import com.sylvain.chat.system.repository.FriendshipRepository;
import com.sylvain.chat.system.repository.PersonRepository;
import com.sylvain.chat.system.representation.FriendshipRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class FriendshipService {
    private final PersonRepository personRepository;
    private final FriendshipRepository friendshipRepository;

    /**
     * Save a new friendship after a request is accepted by recipient from FriendRequestService
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String user_username, String friend_username){
        //check existence
        friendshipNotExists(user_username,friend_username);

        Person user = findPerson(user_username);
        Person friend = findPerson(friend_username);

        Friendship friendship = new Friendship(user,friend, Year.now().getValue());
        friendshipRepository.save(friendship);
    }

    /**
     * Search user's friendships
     * the user is saved in Spring Security context
     * @return a collection of his friendships
     */
    public Set<FriendshipRepresentation> findFriendships(){
        String username = CurrentUserUtils.getCurrentUsername();
        Person person = findPerson(username);
        return person.getFriendships(username).stream().map(Friendship::toFriendshipRepresentation).collect(Collectors.toSet());
    }

    /**
     * find a person from neo4j db
     * @param username his username
     * @return this person
     */
    private Person findPerson(String username){
        return personRepository.findByUsername(username).orElseThrow(
                () -> new PersonNotFoundException(ImmutableMap.of("username",username))
        );
    }

    /**
     * check a friendship is not already created
     * @param user_username the start node username
     * @param friend_username the end node username
     */
    public void friendshipNotExists(String user_username, String friend_username){
        boolean exist = friendshipRepository.findFriendshipByUsernames(user_username, friend_username).isPresent();
        if(exist)
            throw new FriendshipAlreadyExistsException(ImmutableMap.of("user_username",user_username,"friend_username",friend_username));
    }
}
