package com.sylvain.chat.system.controller;

import com.sylvain.chat.system.DTO.FriendRequestDTO;
import com.sylvain.chat.system.representation.FriendRequestRepresentation;
import com.sylvain.chat.system.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
@RequestMapping("/api/friend/request")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    /**
     * check a user's received requests
     * this user must have authenticated to check his profile
     * @return his List<FriendRequestRepresentation>
     */
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<FriendRequestRepresentation>> friendRequestsReceived(){
        List<FriendRequestRepresentation> friendRequests = friendRequestService.friendRequestsReceived();
        return ResponseEntity.ok().body(friendRequests);
    }

    /**
     * the current user send a friend request to another user{username: '$username'}
     * @param friendRequestDTO which contains the recipient's username
     * @return Void
     */
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Void> friendRequest(@RequestBody @Valid FriendRequestDTO friendRequestDTO){
        friendRequestService.save(friendRequestDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * accept a friend request
     * @param id_friendRequest
     * @return
     */
    @PutMapping("/{id_friendRequest}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Void> accept(@PathVariable String id_friendRequest){
        friendRequestService.accept(id_friendRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id_friendRequest}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Void> refuse(@PathVariable String id_friendRequest){
        friendRequestService.refuse(id_friendRequest);
        return ResponseEntity.ok().build();
    }

}
