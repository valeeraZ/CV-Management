package com.sylvain.chat.system.entity;

import com.sylvain.chat.system.repository.UserRepository;
import com.sylvain.chat.system.representation.FriendshipRepresentation;
import com.sylvain.chat.system.utils.PersonToUser;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "Friends")
@Getter
public class Friendship {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person user;

    @EndNode
    private Person friend;

    private int since;

    public Friendship(Person user, Person friend, int since) {
        this.user = user;
        this.friend = friend;
        this.since = since;
    }

    public FriendshipRepresentation toFriendshipRepresentation(){
        /*User user = userRepository.findByUsername(this.user.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("username.notfound")
        );

        User friend = userRepository.findByUsername(this.friend.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("username.notfound")
        );*/

        return FriendshipRepresentation.builder()
                .user(PersonToUser.toUserPublicRepresentation(user))
                .friend(PersonToUser.toUserPublicRepresentation(friend))
                .since(since).build();
    }
}
