package com.sylvain.cvmanagement.system.repository;

import com.sylvain.cvmanagement.system.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
