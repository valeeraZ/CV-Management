package com.sylvain.chat.system.controller;

import com.sylvain.chat.system.representation.FriendshipRepresentation;
import com.sylvain.chat.system.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
@RequestMapping("/api/friend")
public class FriendshipController {
    private final FriendshipService friendshipService;

    /**
     * get the collection of user's friends
     * the user must have authenticated before
     * @return Set<FriendshipRepresentation>
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Set<FriendshipRepresentation>> friendships(){
        Set<FriendshipRepresentation> friendships = friendshipService.findFriendships();
        return ResponseEntity.ok().body(friendships);
    }

    /*@PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Void> addFriends(@RequestBody FriendshipDTO friendshipDTO){
        friendshipService.save(friendshipDTO);
        return ResponseEntity.ok().build();
    }*/
}
