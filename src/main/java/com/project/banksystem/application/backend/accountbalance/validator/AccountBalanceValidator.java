package com.project.banksystem.application.backend.accountbalance.validator;

import com.project.banksystem.api.request.dto.account.body.AccountBalanceCreateData;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.InsufficientFundException;
import com.project.banksystem.infrastructure.model.AccountBalance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountBalanceValidator {


    public void validateCanCreate(final AccountBalanceCreateData accountBalanceCreateData) {
        BigDecimal ledgerBalance = accountBalanceCreateData.getLedgerBalance();
        BigDecimal availableBalance = accountBalanceCreateData.getAvailableBalance();


        if (ledgerBalance.compareTo(availableBalance) != 0) {
            throw new BusinessException("Pre set ledger balance must match pre set available balance.");
        }
    }

    public void validateCanSubtractReserveBalance(final AccountBalance accountBalance,
                                                  final BigDecimal amount) {
        if (accountBalance.getReserveBalance().compareTo(amount) < 0) {
            throw new InsufficientFundException("Account reserve balance is insufficient.");
        }
    }

    public void validateCanSubtractAvailableBalance(final AccountBalance accountBalance,
                                                    final BigDecimal amount) {
        if (accountBalance.getAvailableBalance().compareTo(amount) < 0) {
            throw new InsufficientFundException("Account available balance is insufficient.");
        }
    }

    public void validateIsOverdrafted(final AccountBalance accountBalance) {
        if (accountBalance.isOverdrafted()) {
            throw new BusinessException("Account is not safe for deduction due to overdrafted.");
        }
    }
}
