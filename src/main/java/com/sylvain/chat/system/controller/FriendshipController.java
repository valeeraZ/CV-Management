package com.sylvain.chat.system.controller;

import com.sylvain.chat.system.DTO.FriendshipDTO;
import com.sylvain.chat.system.entity.Friendship;
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
@RequestMapping("/api/friendship")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Set<FriendshipRepresentation>> friendships(@PathVariable String username){
        Set<FriendshipRepresentation> friendships = friendshipService.findFriendships(username);
        return ResponseEntity.ok().body(friendships);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Void> addFriends(@RequestBody FriendshipDTO friendshipDTO){
        friendshipService.save(friendshipDTO);
        return ResponseEntity.ok().build();
    }
}
