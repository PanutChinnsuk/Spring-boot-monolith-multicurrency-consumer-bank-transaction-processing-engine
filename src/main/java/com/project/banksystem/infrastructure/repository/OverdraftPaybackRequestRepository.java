package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.OverdraftPaybackRequest;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OverdraftPaybackRequestRepository extends JpaRepository<OverdraftPaybackRequest, UUID> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT opr FROM OverdraftPaybackRequest opr" +
            " LEFT JOIN FETCH opr.overdraftedAccount oa" +
            " LEFT JOIN FETCH oa.fxRate" +
            " WHERE opr.requestId = :requestId")
    Optional<OverdraftPaybackRequest> findForApproval(@Param("requestId") UUID requestId);
}
