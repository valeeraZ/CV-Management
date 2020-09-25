package com.sylvain.chat.system.entity;

import com.sylvain.chat.system.representation.FriendshipRepresentation;
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
        return FriendshipRepresentation.builder()
                .user_username(user.getUsername())
                .friend_username(friend.getUsername())
                .since(since).build();
    }
}
