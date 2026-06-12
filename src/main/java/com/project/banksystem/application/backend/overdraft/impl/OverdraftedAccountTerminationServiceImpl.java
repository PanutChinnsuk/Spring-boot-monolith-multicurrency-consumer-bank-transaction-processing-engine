package com.project.banksystem.application.backend.overdraft.impl;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.banktransaction.transaction.impl.resultrecords.OverdraftRollbackResult;
import com.project.banksystem.application.backend.overdraft.internal.account.query.OverdraftedAccountFinder;
import com.project.banksystem.application.backend.overdraft.internal.account.service.InternalOverdraftReversalService;
import com.project.banksystem.application.backend.overdraft.internal.account.validator.OverdraftedAccountValidator;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftedAccountStatus;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.AccountBalance;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OverdraftedAccountTerminationServiceImpl {
    private final OverdraftedAccountFinder overdraftedAccountFinder;
    private final OverdraftedAccountValidator overdraftedAccountValidator;
    private final AccountValidator accountValidator;
    private final InternalOverdraftReversalService internalOverdraftReversalService;

    public OverdraftedAccountTerminationServiceImpl(OverdraftedAccountFinder overdraftedAccountFinder,
                                                    OverdraftedAccountValidator overdraftedAccountValidator,
                                                    AccountValidator accountValidator,
                                                    InternalOverdraftReversalService internalOverdraftReversalService) {
        this.overdraftedAccountFinder = overdraftedAccountFinder;
        this.overdraftedAccountValidator = overdraftedAccountValidator;
        this.accountValidator = accountValidator;
        this.internalOverdraftReversalService = internalOverdraftReversalService;
    }




    public Result terminateOverdraftedAccount(final UUID overdraftedAccountId) {
        OverdraftedAccount overdraftedAccount = overdraftedAccountFinder
                .findById(overdraftedAccountId);
        overdraftedAccountValidator.validateOperational(overdraftedAccount);
        overdraftedAccountValidator.validateCanForceTerminate(overdraftedAccount);

        Account account = overdraftedAccount.getAccount();
        accountValidator.validateAccountNotClosed(account);

        AccountBalance accountBalance = account.getAccountBalance();

        BigDecimal reversingAmount = overdraftedAccount.getConvertedAmount();
        BigDecimal overdraftRemovalAmount = overdraftedAccount.getTrackedConvertedAmount();

        OverdraftRollbackResult result = internalOverdraftReversalService
                .getSettledOverdraftAmount(overdraftedAccount, reversingAmount);

        BigDecimal settled = result.settledAmount().add(overdraftRemovalAmount);

        accountBalance.increaseAvailableBalance(result.compensatedAmount());
        accountBalance.increaseLedger(settled);
        accountBalance.decreaseOverdraft(overdraftRemovalAmount);

        overdraftedAccount.changeStatus(OverdraftedAccountStatus.REVERSAL_CLOSED);
        return DataResultBuilder.build("Successfully terminated overdrafted account.");
    }
}
