package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.Credential;
import com.project.banksystem.infrastructure.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, UUID> {
    boolean existsByUser(User user);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Credential> findCredentialById(UUID id);
}
