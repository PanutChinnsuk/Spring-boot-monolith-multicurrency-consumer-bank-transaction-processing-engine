package com.project.banksystem.application.backend.account.validator;

import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Account;

import java.util.EnumSet;

public final class AccountStatusValidator {
    private AccountStatusValidator() {

    }

    private static final EnumSet<AccountStatus> notUpdatableAccountStatus = EnumSet.of(
            AccountStatus.PENDING_APPROVAL,
            AccountStatus.CLOSED
    );

    public static void assertUpdateStatus(final Account account,
                                        final AccountStatus accountStatus) {
        if (notUpdatableAccountStatus.contains(accountStatus)) {
            throw new BusinessException("Account status is prohibited.");
        }
        if (account.getAccountStatus() == accountStatus) {
            throw new BusinessException("Account already have this status.");
        }
    }

}
