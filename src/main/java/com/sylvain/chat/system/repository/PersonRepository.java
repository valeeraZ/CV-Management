package com.sylvain.chat.system.repository;

import com.sylvain.chat.system.entity.Friendship;
import com.sylvain.chat.system.entity.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, String> {
    Optional<Person> findByUsername(String username);
}
