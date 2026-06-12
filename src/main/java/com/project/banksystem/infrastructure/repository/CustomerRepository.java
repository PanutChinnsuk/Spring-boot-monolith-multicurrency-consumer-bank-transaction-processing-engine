package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @EntityGraph(attributePaths = {
            "customerProfile"
    })
    Optional<Customer> findFirstByCustomerId(UUID customerId);
}
