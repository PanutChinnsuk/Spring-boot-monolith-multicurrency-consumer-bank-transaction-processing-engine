package com.project.banksystem.infrastructure.repository;


import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
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
public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByCustomer(Customer customer);
    Optional<Account> findByCustomer(Customer customer);

    @EntityGraph(attributePaths = {
            "customer",
            "customer.customerProfile",
            "accountBalance"
    })
    Optional<Account> findByAccountNumber(String accountNumber);


    boolean existsByAccountNumber(String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {
            "customer",
            "customer.customerProfile",
            "accountBalance"
    })
    @Query(value = "SELECT a FROM Account a WHERE a.accountNumber IN :accountNumbers" +
            " AND a.accountStatus = :accountStatus ORDER BY a.accountId ASC")
    List<Account> findByAccountNumberInAndAccountStatus(@Param("accountNumbers") Set<String> accountNumbers,
                                                        @Param("accountStatus") AccountStatus accountStatus);


    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "SELECT 1 FROM Account a WHERE" +
            " a.accountId = :accountId AND" +
            " a.customer.customerId = :customerId)" +
            " THEN true ELSE false END")
    boolean isAccountBelongToCustomer(@Param("accountId") UUID accountId,
                                      @Param("customerId") UUID customerId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {
            "customer",
            "customer.customerProfile",
            "accountBalance"
    })
    @Query(value = "SELECT a FROM Account a WHERE a.accountId = :accountId")
    Optional<Account> findByIdWithCustomerLoaded(@Param("accountId") UUID accountId);



}
