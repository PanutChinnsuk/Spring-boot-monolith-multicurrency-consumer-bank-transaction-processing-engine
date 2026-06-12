package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.infrastructure.model.OverdraftPaybackLine;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface OverdraftPaybackLineRepository extends JpaRepository<OverdraftPaybackLine, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT odpl FROM OverdraftPaybackLine odpl" +
            " JOIN FETCH odpl.account a" +
            " JOIN FETCH a.customer c" +
            " JOIN FETCH c.customerProfile" +
            " JOIN FETCH a.accountBalance" +
            " WHERE odpl.overdraftPaybackRequest.requestId = :requestId")
    List<OverdraftPaybackLine> findForProcessing(@Param("requestId") UUID requestId);
}
