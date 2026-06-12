package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.core.enums.role.UserAccess;
import com.project.banksystem.infrastructure.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByUserAccess(UserAccess userAccess);

    Optional<Role> findByUserAccess(UserAccess userAccess);
}
