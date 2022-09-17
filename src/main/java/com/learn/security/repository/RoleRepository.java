package com.learn.security.repository;

import com.learn.security.entity.Role;
import com.learn.security.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(RoleType role);
}
