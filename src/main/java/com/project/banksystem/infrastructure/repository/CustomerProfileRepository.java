package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerProfile;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, UUID> {
    boolean existsByNationalId(String nationalId);




    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT cmp FROM CustomerProfile cmp" +
            " JOIN FETCH cmp.customer" +
            " WHERE cmp.id = :customerId")
    Optional<CustomerProfile> findByCustomerId(@Param("customerId") UUID customerId);

}
