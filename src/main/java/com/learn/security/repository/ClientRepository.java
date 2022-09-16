package com.learn.security.repository;

import com.learn.security.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsById(Long id);

    Optional<Client> findClientByEmail(String email);
}
