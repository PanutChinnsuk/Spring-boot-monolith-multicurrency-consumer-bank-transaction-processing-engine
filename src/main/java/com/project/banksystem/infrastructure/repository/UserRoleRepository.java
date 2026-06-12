package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.Role;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    boolean existsByRole(Role role);
    boolean existsByUserAndRole(User user, Role role);


}
