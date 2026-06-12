package com.project.banksystem.infrastructure.repository;

import com.project.banksystem.core.enums.account.AccountPurpose;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.model.InternalAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface InternalAccountRepository extends JpaRepository<InternalAccount, UUID> {


    @Query(value = "SELECT CASE WHEN EXISTS(" +
            "SELECT 1 FROM InternalAccount i WHERE i.branch.branchCode = :branchCode" +
            " AND (i.account.accountId = :accountId OR i.label = :label OR i.accountPurpose =" +
            " :accountPurpose OR i.internalCode = :internalCode)) THEN true ELSE false END")
    boolean alreadyExist(@Param("branchCode") String branchCode,
                         @Param("accountId") UUID accountId,
                         @Param("label")String label,
                         @Param("accountPurpose") AccountPurpose accountPurpose,
                         @Param("internalCode") String string);


    @EntityGraph(attributePaths = {
            "branch",
            "account"
    })
    @Query(value = "SELECT i FROM InternalAccount i WHERE " +
            "i.branch.branchName IN :branchNames AND i.accountPurpose IN :accountPurposes" +
            " AND i.account.accountStatus = :accountStatus ORDER BY i.internalAccountId ASC")
    List<InternalAccount> getInternalAccountsBySet(@Param("branchNames") Set<String> branchNames,
                                                      @Param("accountPurposes") Set<AccountPurpose> accountPurposes,
                                                      @Param("accountStatus")AccountStatus accountStatus);


    @EntityGraph(attributePaths = {
            "branch",
            "account"
    })
    @Query(value = "SELECT DISTINCT i FROM InternalAccount i WHERE" +
            " i.internalCode IN :internalCodes" +
            " ANd i.account.currencyCode = :baseCurrency" +
            " AND i.account.accountStatus = :accountStatus")
    List<InternalAccount> getInternalAccountsByInternalCodes(@Param("internalCodes") Set<String> internalCodes,
                                                     @Param("baseCurrency") CurrencyCode baseCurrency,
                                                     @Param("accountStatus")AccountStatus accountStatus);



    @EntityGraph(attributePaths = {
            "branch",
            "account"
    })
    @Query(value = "SELECT i FROM InternalAccount i WHERE " +
            "i.branch.branchName = :branchName AND i.accountPurpose = :accountPurpose" +
            " AND i.account.accountStatus = :accountStatus AND i.account.currencyCode = :baseCurrency")
    Optional<InternalAccount> getInternalAccount(@Param("branchName") String branchNames,
                                                 @Param("accountPurpose") AccountPurpose accountPurposes,
                                                 @Param("accountStatus")AccountStatus accountStatus,
                                                 @Param("baseCurrency")CurrencyCode currencyCode);
}
