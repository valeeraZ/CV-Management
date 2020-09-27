package com.sylvain.chat.system.repository;

import com.sylvain.chat.system.entity.FriendRequest;
import com.sylvain.chat.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, String> {
    /**
     * get the requests accepted received by the user
     * @param recipient the User entity
     * @return
     */
    Optional<List<FriendRequest>> findFriendRequestsByIsAcceptedTrueAndRecipient(User recipient);

    /**
     * get the requests sent by user
     * @param sender the user entity
     * @return
     */
    Optional<List<FriendRequest>> findFriendRequestsBySender(User sender);

    /**
     * find a request with tuple(sender, recipient)
     * @param sender user entity
     * @param recipient user entity
     * @return
     */
    Optional<FriendRequest> findFriendRequestsBySenderAndRecipient(User sender, User recipient);
}
