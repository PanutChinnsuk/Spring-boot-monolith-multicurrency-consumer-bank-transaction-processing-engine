package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.core.enums.aml.AmlCaseStatus;
import com.project.banksystem.infrastructure.model.AmlCase;
import com.project.banksystem.infrastructure.model.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AmlCaseRepository extends JpaRepository<AmlCase, UUID> {

    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "SELECT 1 FROM AmlCase ac WHERE ac.customer.customerId = :customerId AND" +
            " ac.label = :label)" +
            " THEN true ELSE false END")
    boolean existsByCustomerIdAndLabel(@Param("customerId") UUID customerId,
                                       @Param("label") String label);



    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "SELECT 1 FROM AmlCase ac WHERE ac.customer.customerId IN :customerIds AND" +
            " ac.amlCaseStatus != :closedStatus)" +
            " THEN true ELSE false END")
    boolean anyActiveAmlCase(@Param("customerIds") Set<UUID> customerId,
                                                  @Param("closedStatus")AmlCaseStatus closedStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ac FROM AmlCase ac" +
            " JOIN FETCH ac.customer c" +
            " JOIN FETCH c.customerProfile" +
            " WHERE ac.caseId = :caseId")
    Optional<AmlCase> findByAmlCaseId(@Param("caseId") UUID caseId);

}
