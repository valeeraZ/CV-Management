package com.sylvain.chat.system.entity;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Setter
@Getter
public class Person {
    @Id
    private String id;

    private String name;

    private String username;

    @Relationship(type = "Friends", direction = Relationship.UNDIRECTED)
    public Set<Friendship> friendships = new HashSet<>();

    public Set<Friendship> getFriendships(String username){
        Set<Friendship> friendships = new HashSet<>();
        for (Friendship friendship : this.friendships) {
            Person start = friendship.getUser();
            Person end = friendship.getFriend();
            int since = friendship.getSince();
            Person user = start.getUsername().equals(username) ? start : end;
            Person friend = start.getUsername().equals(username) ? end : start;
            Friendship correctFriendship = new Friendship(user,friend,since);
            friendships.add(correctFriendship);
        }
        return friendships;
    }

    /*public Person(String name, String username){
        this.name = name;
        this.username = username;
    }*/

    @Builder
    public Person(String id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
