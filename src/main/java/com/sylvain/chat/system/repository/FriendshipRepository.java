package com.sylvain.chat.system.repository;

import com.sylvain.chat.system.entity.Friendship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FriendshipRepository extends Neo4jRepository<Friendship, String> {
    @Query("MATCH p = (a:Person{username:$user_username})-[r:Friends]-(b:Person{username:$friend_username}) RETURN p")
    Optional<Friendship> findFriendshipByUsernames(String user_username, String friend_username);

    @Query("MATCH p = (a:Person{username:$username})-[r:Friends]-(b:Person) RETURN p")
    Optional<Set<Friendship>> findFriendshipsByUsername(String username);
}
