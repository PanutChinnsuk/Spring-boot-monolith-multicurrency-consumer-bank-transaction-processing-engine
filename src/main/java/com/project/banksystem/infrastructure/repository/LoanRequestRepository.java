package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.core.enums.loan.LoanRequestStatus;
import com.project.banksystem.infrastructure.model.LoanRequest;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, UUID> {


    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "SELECT 1 FROM LoanRequest lr" +
            " WHERE lr.customer.customerId = :customerId" +
            " AND lr.loanRequestStatus = :pendingStatus)" +
            " THEN true ELSE false END")
    boolean pendingExists(@Param("customerId") UUID customerId,
                          @Param("pendingStatus")LoanRequestStatus pendingStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT lr FROM LoanRequest lr" +
            " JOIN FETCH lr.account a" +
            " JOIN FETCH a.accountBalance" +
            " JOIN FETCH a.customer c" +
            " JOIN FETCH c.customerProfile" +
            " WHERE lr.loanRequestId = :loanRequestId")
    Optional<LoanRequest> findForApprove(@Param("loanRequestId") UUID loanRequestId);
}
