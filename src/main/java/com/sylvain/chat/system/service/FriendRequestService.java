package com.sylvain.chat.system.service;

import com.google.common.collect.ImmutableMap;
import com.sylvain.chat.security.utils.CurrentUserUtils;
import com.sylvain.chat.system.DTO.FriendRequestDTO;
import com.sylvain.chat.system.entity.FriendRequest;
import com.sylvain.chat.system.entity.User;
import com.sylvain.chat.system.exception.FriendRequestIdNotExistsException;
import com.sylvain.chat.system.repository.FriendRequestRepository;
import com.sylvain.chat.system.representation.FriendRequestRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class FriendRequestService {
    private final UserService userService;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipService friendshipService;
    private final CurrentUserUtils currentUserUtils;

    /**
     * save a friend request to recipient
     * Exception 1: the friendship is already created in neo4j db
     * Exception 2: the user doesn't exist in user table in mysql db
     * @param friendRequestDTO contains the recipient's username
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(FriendRequestDTO friendRequestDTO){
        String sender_username = CurrentUserUtils.getCurrentUsername();
        String recipient_username = friendRequestDTO.getRecipient_username();

        //check the existence of friendship
        friendshipService.friendshipNotExists(sender_username,recipient_username);

        User sender = userService.find(sender_username);
        User recipient = userService.find(recipient_username);

        //db operation after check existence
        if(friendRequestNotExists(sender, recipient)) {
            friendRequestRepository.save(new FriendRequest(sender, recipient));
            //then something to do with websocket
        }
    }

    /**
     * accept a request: update isAccepted = true
     * then create a new friendship
     * @param id_friendRequest id of request
     */
    @Transactional(rollbackFor = Exception.class)
    public void accept(String id_friendRequest){
        FriendRequest friendRequest = friendRequestRepository.findById(id_friendRequest).orElseThrow(
                () -> new FriendRequestIdNotExistsException(ImmutableMap.of("id_friendRequest",id_friendRequest))
        );

        User user = currentUserUtils.getCurrentUser();
        if (!user.getFriendRequestsReceived().contains(friendRequest))
            throw new AccessDeniedException("User tries to accept other's resource: Friend Request " + id_friendRequest);

        friendRequest.setIsAccepted(true);
        friendRequestRepository.save(friendRequest);

        String user_username = friendRequest.getSender().getUsername();
        String friend_username = friendRequest.getRecipient().getUsername();

        friendshipService.save(user_username, friend_username);
    }

    @Transactional(rollbackFor = Exception.class)
    public void refuse(String id_friendRequest){
        FriendRequest friendRequest = friendRequestRepository.findById(id_friendRequest).orElseThrow(
                () -> new FriendRequestIdNotExistsException(ImmutableMap.of("id_friendRequest",id_friendRequest))
        );

        User user = currentUserUtils.getCurrentUser();
        if (!user.getFriendRequestsReceived().contains(friendRequest))
            throw new AccessDeniedException("User tries to accept other's resource: Friend Request " + id_friendRequest);
        user.getFriendRequestsReceived().remove(friendRequest);
        friendRequestRepository.delete(friendRequest);
    }

    /**
     * get the requests sent by an user
     */
    public List<FriendRequestRepresentation> findFriendRequestsSent(){
        String sender_username = CurrentUserUtils.getCurrentUsername();
        User sender = userService.find(sender_username);
        return sender.getFriendRequestsSent().stream().map(FriendRequest::toFriendRequestRepresentation).collect(Collectors.toList());
    }

    /**
     * get the requests received by an user
     */
    public List<FriendRequestRepresentation> friendRequestsReceived(){
        String recipient_username = CurrentUserUtils.getCurrentUsername();
        User recipient = userService.find(recipient_username);
        return recipient.getFriendRequestsReceived().stream().map(FriendRequest::toFriendRequestRepresentation).collect(Collectors.toList());
    }

    /**
     * check the existence of a request
     * @param sender user entity
     * @param recipient user entity
     * @return true if not exists
     */
    private boolean friendRequestNotExists(User sender, User recipient){
        return !friendRequestRepository.findFriendRequestsBySenderAndRecipient(sender, recipient).isPresent();
    }
}
