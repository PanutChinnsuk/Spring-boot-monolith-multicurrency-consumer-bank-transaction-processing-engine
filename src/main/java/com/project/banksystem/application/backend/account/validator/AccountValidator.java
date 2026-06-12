package com.project.banksystem.application.backend.account.validator;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.AccountBalance;
import com.project.banksystem.infrastructure.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AccountValidator {
    private final AccountRepository accountRepository;
    private final CustomerValidator customerValidator;

    public AccountValidator(AccountRepository accountRepository,
                            CustomerValidator customerValidator) {
        this.accountRepository = accountRepository;
        this.customerValidator = customerValidator;
    }

    private boolean greaterThanZero(final BigDecimal bigDecimal) {
        return bigDecimal.signum() > 0;
    }

    public void validateAccountOperational(final Account account) {
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new BusinessException("Account is not operational.");
        }
        customerValidator.validateCustomerOperational(account.getCustomer());
    }

    public void validateAccountNotClosed(final Account account) {
        if (account.getAccountStatus() == AccountStatus.CLOSED) {
            throw new BusinessException("Account is closed.");
        }
        customerValidator.validateCustomerNotTerminated(account.getCustomer());
    }

    public void validateAccountNotPending(final Account account) {
        if (account.getAccountStatus() == AccountStatus.PENDING_APPROVAL) {
            throw new BusinessException("Account is on pending.");
        }
    }

    public void validateAccountCanClose(final Account account) {
        AccountBalance accountBalance = account.getAccountBalance();

        if (account.getAccountStatus() == AccountStatus.CLOSED) {
            throw new BusinessException("Account is already closed.");
        }

        if (greaterThanZero(accountBalance.getAvailableBalance()) ||
        greaterThanZero(accountBalance.getLedgerBalance())) {
            throw new BusinessException("Account balance is still available.");
        }
    }

    public void validateAccountCanReinstate(final Account account) {
        if (account.getAccountStatus() != AccountStatus.CLOSED) {
            throw new BusinessException("Account is not closed.");
        }
        customerValidator.validateCustomerOperational(account.getCustomer());
    }

    public void validateAccountCanBeApprove(final Account account) {
        if (account.getAccountStatus() != AccountStatus.PENDING_APPROVAL) {
            throw new BusinessException("Account is not pending approval.");
        }
        customerValidator.validateCustomerOperational(account.getCustomer());
    }

    public void validateAccountBelongToCustomer(final UUID accountId,
                                                final UUID customerId) {
        if (!accountRepository.isAccountBelongToCustomer(accountId, customerId)) {
            throw new BusinessException("Account does not belong to customer.");
        }
    }

    public void validateAccountBelongToCustomer(final Account account,
                                                final UUID customerId) {
        UUID accountCustomerId = account.getCustomer().getCustomerId();

        if (!accountCustomerId.equals(customerId)) {
            throw new BusinessException("Account does not belong to customer");
        }
    }


    public void validateAccountCustomerIdMatchedCurrentCustomerId(final Account account) {
        if (!account.getCustomer().getCustomerId().equals(CurrentUser.getCurrentUserCustomerId())) {
            throw new BusinessException("Account does not belong to customer.");
        }
    }



}
