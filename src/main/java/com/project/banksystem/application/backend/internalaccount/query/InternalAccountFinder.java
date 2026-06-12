package com.project.banksystem.application.backend.internalaccount.query;

import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.account.AccountPurpose;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.InternalAccount;
import com.project.banksystem.infrastructure.repository.InternalAccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class InternalAccountFinder {
    private final InternalAccountRepository internalAccountRepository;
    private final SystemConfig systemConfig;

    public InternalAccountFinder(InternalAccountRepository internalAccountRepository,
                                 SystemConfig systemConfig) {
        this.internalAccountRepository = internalAccountRepository;
        this.systemConfig = systemConfig;
    }


    public List<InternalAccount> getOperationalInternalAccounts(final Set<String> branchNames,
                                               final Set<AccountPurpose> accountPurpose) {
        return internalAccountRepository.getInternalAccountsBySet(branchNames,
                        accountPurpose,
                        AccountStatus.ACTIVE);
    }

    public Account getOperationalBaseCurrencyByBranchAndPurpose(final String branchName,
                                                                final AccountPurpose accountPurpose) {
        return internalAccountRepository.getInternalAccount(branchName,
                accountPurpose,
                AccountStatus.ACTIVE, systemConfig.getBaseCurrencyCurrencyCode())
                .orElseThrow(() -> new NotFoundException("Base currency " +
                        "internal account not found.")).getAccount();
    }

    public Map<String, Account> getOperationalByBaseCurrencyByInternalCodes(final Set<String> internalCodes) {
        List<InternalAccount> internalAccounts = internalAccountRepository.getInternalAccountsByInternalCodes(
                internalCodes,
                systemConfig.getBaseCurrencyCurrencyCode(),
                AccountStatus.ACTIVE
        );

        if (internalAccounts.size() != internalCodes.size()) {
            throw new NotFoundException("One or more internal accounts is not valid.");
        }

        return internalAccounts.stream().collect(Collectors.toMap(
            i -> i.getInternalCode(),
                InternalAccount::getAccount
        ));

    }
}
