package com.sylvain.cvmanagement.system.repository;

import com.sylvain.cvmanagement.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}
