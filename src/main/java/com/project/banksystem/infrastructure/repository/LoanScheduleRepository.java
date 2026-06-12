package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.infrastructure.model.LoanSchedule;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanScheduleRepository extends JpaRepository<LoanSchedule, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ls FROM LoanSchedule ls" +
            " JOIN FETCH ls.loan l" +
            " JOIN FETCH l.account a" +
            " JOIN FETCH a.customer c" +
            " JOIN FETCH c.customerProfile" +
            " LEFT JOIN FETCH l.usedFxRate" +
            " WHERE l.loanId = :loanId")
    List<LoanSchedule> findForPayment(@Param("loanId") UUID loanId);
}
