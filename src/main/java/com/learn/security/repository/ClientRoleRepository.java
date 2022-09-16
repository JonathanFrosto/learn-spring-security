package com.learn.security.repository;

import com.learn.security.entity.Client;
import com.learn.security.entity.ClientRole;
import com.learn.security.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRoleRepository extends JpaRepository<ClientRole, UUID> {

    ClientRole findByRole(RoleType role);
}
