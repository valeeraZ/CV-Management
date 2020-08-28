package com.sylvain.chat.system.repository;

import com.sylvain.chat.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}
