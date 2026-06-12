package com.project.banksystem.application.backend.overdraft.internal.account.validator;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.config.properties.OverdraftAccountConfig;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftedAccountStatus;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OverdraftedAccountValidator {
    private final AccountValidator accountValidator;
    private final OverdraftAccountConfig overdraftAccountConfig;

    public OverdraftedAccountValidator(AccountValidator accountValidator,
                                       OverdraftAccountConfig overdraftAccountConfig) {
        this.accountValidator = accountValidator;
        this.overdraftAccountConfig = overdraftAccountConfig;
    }



    public void validateOverdraftedAccountBelongToCurrentUser(final OverdraftedAccount overdraftedAccount) {
        boolean allowedNonActiveAccount = overdraftAccountConfig.allowedNonActiveAccount();
        boolean currentUserIsAdmin = CurrentUser.isUserSystemAdministrator();

        Account account = overdraftedAccount.getAccount();
        UUID customerId = account.getCustomer().getCustomerId();

        UUID currentUserCustomerId = CurrentUser.getCurrentUserCustomerId();

        if (!currentUserIsAdmin && !customerId.equals(currentUserCustomerId)) {
            throw new BusinessException("Overdrafted account does not belong to customer.");
        }

        if (!allowedNonActiveAccount) {
            accountValidator.validateAccountOperational(account);
            return;
        }

        accountValidator.validateAccountNotClosed(account);
        accountValidator.validateAccountNotPending(account);
    }

    public void validateMatchedLocalCurrency(final OverdraftedAccount overdraftedAccount) {
        CurrencyCode fromAccount = overdraftedAccount.getAccount().getCurrencyCode();
        CurrencyCode fromOverdraft = overdraftedAccount.getCurrencyCode();

        if (!fromAccount.equals(fromOverdraft)) {
            throw new SystemMisusedException("Account's currency must matched overdraft's currency.");
        }
    }

    public void validateOperational(final OverdraftedAccount overdraftedAccount)  {
        OverdraftedAccountStatus status = overdraftedAccount.getOverdraftedAccountStatus();
        boolean isPaid = overdraftedAccount.getPaid();

        if (status != OverdraftedAccountStatus.ACTIVE) {
            throw new BusinessException("Overdrafted account is not active.");
        }

        if (isPaid) {
            throw new BusinessException("Overdrafted account is already cleared.");
        }
    }

    public void validateCanForceTerminate(final OverdraftedAccount overdraftedAccount) {
        if (!overdraftedAccount.canForceReversal()) {
            throw new BusinessException("A transaction related overdrafted account" +
                    " can't be force reversal.");
        }
    }
}
