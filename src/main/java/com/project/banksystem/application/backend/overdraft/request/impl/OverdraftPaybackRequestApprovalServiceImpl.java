package com.project.banksystem.application.backend.overdraft.request.impl;

import com.project.banksystem.application.backend.currency.CurrencyConversionService;
import com.project.banksystem.application.backend.overdraft.internal.account.validator.OverdraftedAccountValidator;
import com.project.banksystem.application.backend.overdraft.internal.line.query.OverdraftPaybackLineFinder;
import com.project.banksystem.application.backend.overdraft.internal.line.validator.IgnoredOverdraftPaybackRoles;
import com.project.banksystem.application.backend.overdraft.internal.request.query.OverdraftPaybackRequestFinder;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackLineDirection;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRequestStatus;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRole;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftedAccountStatus;
import com.project.banksystem.infrastructure.exception.ex.InsufficientFundException;
import com.project.banksystem.infrastructure.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@Service
public class OverdraftPaybackRequestApprovalServiceImpl {
    private final OverdraftedAccountValidator overdraftedAccountValidator;
    private final OverdraftPaybackLineFinder overdraftPaybackLineFinder;
    private final CurrencyConversionService currencyConversionService;
    private final OverdraftPaybackRequestFinder overdraftPaybackRequestFinder;


    public OverdraftPaybackRequestApprovalServiceImpl(OverdraftedAccountValidator overdraftedAccountValidator,
                                                      OverdraftPaybackLineFinder overdraftPaybackLineFinder,
                                                      CurrencyConversionService currencyConversionService,
                                                      OverdraftPaybackRequestFinder overdraftPaybackRequestFinder) {
        this.overdraftPaybackLineFinder = overdraftPaybackLineFinder;
        this.overdraftedAccountValidator = overdraftedAccountValidator;
        this.currencyConversionService = currencyConversionService;
        this.overdraftPaybackRequestFinder = overdraftPaybackRequestFinder;
    }

    private final boolean REQUIRED_ACTIVE_ACCOUNTS = true;




    private void handlePaybackSideAccountBalance(final AccountBalance accountBalance,
                                   final BigDecimal payingAmount) {
        if (!accountBalance.isAccountBalanceSufficientFor(payingAmount)) {
            throw new InsufficientFundException("Account does not have sufficient" +
                    " balance for overdraft pay back.");
        }

        accountBalance.deductBothAvailableAndLedger(payingAmount);
    }


    private void refundOverPaid(final AccountBalance accountBalance,
                                final BigDecimal amount) {
        if (amount.signum() <= 0) {return;}

        accountBalance.increaseBothAvailableAndLedger(amount);
    }

    private BigDecimal getSettledAmountForLedgerFromPaidOverdraft(final BigDecimal owned,
                                               final BigDecimal original) {
        BigDecimal currentDebt = owned;
        if (currentDebt.signum() < 0) {
            currentDebt = BigDecimal.ZERO;
        }

        return original.subtract(currentDebt);
    }

    private BigDecimal handleOverdraftedAccount(final OverdraftedAccount overdraftedAccount,
                                          final AccountBalance accountBalance,
                                          final BigDecimal payingAmount) {
        BigDecimal original = overdraftedAccount.getTrackedConvertedAmount();

        BigDecimal after = overdraftedAccount
                .decreaseTrackedConvertedAmount(payingAmount);

        BigDecimal toBaseCurrency = overdraftedAccount.getFxRate() == null
                ? after
                : currencyConversionService
                .getBaseAmountFromConvertedAmount(overdraftedAccount.getFxRate(),
                        after, overdraftedAccount.getOnFxDirection(),
                        overdraftedAccount.getCurrencyCode());

        overdraftedAccount.changeTrackedBaseAmount(toBaseCurrency);

        BigDecimal owned = overdraftedAccount.getTrackedConvertedAmount();

        accountBalance.increaseLedger(getSettledAmountForLedgerFromPaidOverdraft(
                owned, original
        ));

        if (owned.signum() <= 0) {
            overdraftedAccount.changeStatus(OverdraftedAccountStatus.PAID_CLOSED);
        }

        if (owned.signum() < 0) {
            overdraftedAccount.setTrackedBaseZero();
            overdraftedAccount.setTrackedConvertedZero();

            //Overpaid amount
            return owned.abs();
        }



        return BigDecimal.ZERO;
    }

    private BigDecimal getPaybackDeductAmount(final BigDecimal finalAmount,
                                              final OverdraftedAccount overdraftedAccount) {
        BigDecimal deductAmount = finalAmount;
        BigDecimal ownedAmount = overdraftedAccount.getTrackedConvertedAmount();

        if (deductAmount.compareTo(ownedAmount) > 0) {
            deductAmount = ownedAmount;
        }

        return deductAmount;
    }

    private void applyBalance(final OverdraftPaybackLine line,
                              final OverdraftedAccount overdraftedAccount) {
        Account account = line.getAccount();
        BigDecimal finalAmount = line.getFinalAmount();
        OverdraftPaybackLineDirection direction = line.getOverdraftPaybackLineDirection();
        OverdraftPaybackRole role = line.getOverdraftPaybackRole();
        AccountBalance accountBalance = account.getAccountBalance();


        boolean isPaybackSide = direction.isPaybackSide();
        boolean isGainSide = direction.isGainSide();

        //DEPENDS ON BANK POLICY
        boolean affectsOverdraftedAccount = role.isPaybackPrincipal();

        if (isPaybackSide) {
            BigDecimal deductAmount = getPaybackDeductAmount(finalAmount,
                    overdraftedAccount);

            handlePaybackSideAccountBalance(accountBalance, deductAmount);

            if (affectsOverdraftedAccount) {
                BigDecimal overPaidAmount = handleOverdraftedAccount(
                        overdraftedAccount, accountBalance, finalAmount);

                refundOverPaid(accountBalance, overPaidAmount);


            }


        }

        if (isGainSide) {
            accountBalance.increaseBothAvailableAndLedger(finalAmount);
        }
    }


    public OverdraftPaybackRequest approveOverdraftRequest(final OverdraftPaybackRequest overdraftPaybackRequest) {
        OverdraftedAccount overdraftedAccount = overdraftPaybackRequest.getOverdraftedAccount();
        overdraftedAccountValidator.validateOperational(overdraftedAccount);

        List<OverdraftPaybackLine> lines = overdraftPaybackLineFinder
                .findByOverdraftPaybackRequest(overdraftPaybackRequest, REQUIRED_ACTIVE_ACCOUNTS)
                        .stream()
                .filter(l -> !IgnoredOverdraftPaybackRoles.ignoredRoles
                        .contains(l.getOverdraftPaybackRole()))
                        .toList();

        lines.forEach(
                l -> applyBalance(l, overdraftedAccount)
        );


        overdraftPaybackRequest.changeRequestStatus(OverdraftPaybackRequestStatus.APPROVED);


        return overdraftPaybackRequest;
    }

    @Transactional
    public void test(UUID requestId) {
        OverdraftPaybackRequest request = overdraftPaybackRequestFinder
                .findForApproval(requestId);

        approveOverdraftRequest(request);
    }

}
