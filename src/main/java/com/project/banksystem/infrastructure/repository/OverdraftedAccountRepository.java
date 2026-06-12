package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.core.enums.overdraftpayback.OverdraftedAccountStatus;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OverdraftedAccountRepository extends JpaRepository<OverdraftedAccount, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT oa FROM OverdraftedAccount oa" +
            " JOIN FETCH oa.account a" +
            " JOIN FETCH a.customer c" +
            " JOIN FETCH c.customerProfile" +
            " WHERE oa.overdraftedId = :overdraftedId")
    Optional<OverdraftedAccount> findByOverdraftedId(@Param("overdraftedId") UUID overdraftedId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT oa FROM OverdraftedAccount oa" +
            " WHERE oa.transaction.transactionId = :transactionId" +
            " AND oa.overdraftedAccountStatus = :activeStatus" +
            " AND oa.account.accountId IN :accountIds")
    List<OverdraftedAccount> findOverdraftedFromTransaction(@Param("transactionId") UUID transactionId,
                                                            @Param("activeStatus") OverdraftedAccountStatus activeStatus,
                                                            @Param("accountIds")Set<UUID> accountIds);
}
