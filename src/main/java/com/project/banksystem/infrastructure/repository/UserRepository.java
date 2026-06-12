package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUserName(String userName);
    boolean existsByCustomer(Customer customer);
    boolean existsByUserNameAndUserIdNot(String userName, UUID userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {
            "customer",
            "customer.customerProfile",
            "credential"
    })
    Optional<User> findByUserName(String userName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {
            "customer",
            "customer.customerProfile",
            "credential"
    })
    Optional<User> findByUserId(UUID userId);

    @EntityGraph(attributePaths = {
            "userRoles",
            "userRoles.role",
            "customer",
            "customer.customerProfile",
            "credential"
    })
    @Query(value = "SELECT u FROM User u WHERE u.userName = :userName")
    Optional<User> findByUserNameWithCredentialLoaded(String userName);
}
