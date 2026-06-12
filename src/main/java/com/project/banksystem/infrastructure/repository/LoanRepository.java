package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.Loan;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT l FROM Loan l" +
            " JOIN FETCH l.account a" +
            " JOIN FETCH a.customer c" +
            " JOIN FETCH c.customerProfile" +
            " WHERE l.loanId = :loanId")
    Optional<Loan> findForPaymentRequest(@Param("loanId") UUID loanId);
}
