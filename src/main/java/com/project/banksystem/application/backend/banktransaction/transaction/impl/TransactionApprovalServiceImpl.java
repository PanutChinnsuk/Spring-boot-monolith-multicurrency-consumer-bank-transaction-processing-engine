package com.project.banksystem.application.backend.banktransaction.transaction.impl;

import com.project.banksystem.application.backend.accountbalance.validator.AccountBalanceValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionStatusValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionValidator;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.TransactionLineFinder;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.body.TransactionLineQueryData;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.validator.CardTransactionValidator;
import com.project.banksystem.application.backend.banktransaction.transaction.impl.resultrecords.OverdraftResult;
import com.project.banksystem.application.backend.banktransaction.transaction.impl.resultrecords.OverdraftRollbackResult;
import com.project.banksystem.application.backend.banktransaction.transaction.service.TransactionApprovalService;
import com.project.banksystem.application.backend.currency.CurrencyConversionService;
import com.project.banksystem.application.backend.overdraft.internal.account.query.OverdraftedAccountFinder;
import com.project.banksystem.application.backend.overdraft.internal.account.service.InternalOverdraftReversalService;
import com.project.banksystem.application.backend.overdraft.internal.account.service.InternalOverdraftedAccountCreateService;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.ledger.LedgerType;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.*;
import com.project.banksystem.infrastructure.model.Account;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionApprovalServiceImpl implements TransactionApprovalService {
    private final TransactionValidator transactionValidator;
    private final TransactionLineFinder transactionLineFinder;
    private final AccountBalanceValidator accountBalanceValidator;
    private final CardTransactionValidator cardTransactionValidator;
    private final InternalOverdraftedAccountCreateService internalOverdraftedAccountCreateService;
    private final CurrencyConversionService currencyConversionService;
    private final OverdraftedAccountFinder overdraftedAccountFinder;
    private final InternalOverdraftReversalService internalOverdraftReversalService;

    public TransactionApprovalServiceImpl(TransactionValidator transactionValidator,
                                          TransactionLineFinder transactionLineFinder,
                                          AccountBalanceValidator accountBalanceValidator,
                                          CardTransactionValidator cardTransactionValidator,
                                          InternalOverdraftedAccountCreateService internalOverdraftedAccountCreateService,
                                          CurrencyConversionService currencyConversionService,
                                          OverdraftedAccountFinder overdraftedAccountFinder,
                                          InternalOverdraftReversalService internalOverdraftReversalService) {
        this.transactionValidator = transactionValidator;
        this.transactionLineFinder = transactionLineFinder;
        this.accountBalanceValidator = accountBalanceValidator;
        this.cardTransactionValidator = cardTransactionValidator;
        this.internalOverdraftedAccountCreateService = internalOverdraftedAccountCreateService;
        this.currencyConversionService = currencyConversionService;
        this.overdraftedAccountFinder = overdraftedAccountFinder;
        this.internalOverdraftReversalService = internalOverdraftReversalService;
    }

    private final boolean REQUIRED_VALID_ACCOUNTS = true;

    private final EnumSet<BalanceImpact> ignoredBalanceImpact = EnumSet.of(
            BalanceImpact.COMPUTATIONAL
    );

    private void applyAccountBalance(final AccountBalance accountBalance,
                                     final TransactionLineDirection transactionLineDirection,
                                     final LedgerType ledgerType,
                                     final BigDecimal convertedAmount) {
        if (transactionLineDirection == TransactionLineDirection.CREDIT) {
            accountBalance.credit(convertedAmount, ledgerType);
        }else if (transactionLineDirection == TransactionLineDirection.DEBIT) {
            accountBalance.debit(convertedAmount, ledgerType);
        }


    }

    private OverdraftResult returnOverdrafted(final BigDecimal overdraftedAmount) {
        return new OverdraftResult(true, overdraftedAmount);
    }

    private OverdraftResult returnNoOverdrafted() {
        return new OverdraftResult(false, null);
    }

    private BigDecimal calculateOverdraft(final BigDecimal balance,
                                      final BigDecimal trueAmount,
                                      final AccountBalance accountBalance,
                                      final boolean directDebit) {
        BigDecimal finalAmount = trueAmount;

        BigDecimal amount = balance;
        if (amount.signum() <= 0) {
            accountBalance.increaseOverdraft(finalAmount);
            return finalAmount;
        }

        finalAmount = finalAmount.subtract(amount);
        BigDecimal deducted = trueAmount.subtract(finalAmount);

        if (directDebit) {
            accountBalance.deductAvailableBalance(deducted);
        }else {
            accountBalance.deductReserve(deducted);
        }


        accountBalance.increaseOverdraft(finalAmount);
        return finalAmount;
    }

    private OverdraftResult getOverdraftResult(final AccountBalance accountBalance,
                                final boolean isDirectDebit,
                                final boolean isReserveDebit,
                                final BigDecimal convertedReversedAmount) {
        boolean bothAreTrue = isDirectDebit && isReserveDebit;
        boolean bothAreNotTrue = !isDirectDebit && !isReserveDebit;

        if (bothAreTrue || bothAreNotTrue) {
            throw new SystemMisusedException("Invalid debit direction.");
        }

        boolean sufficientBalance = accountBalance.isAccountBalanceSufficientFor(convertedReversedAmount);
        boolean sufficientReserve = accountBalance.isAccountReserveBalanceSufficientFor(convertedReversedAmount);


        boolean sufficientForReversal = (isDirectDebit && sufficientBalance) ||
                (isReserveDebit && sufficientReserve);

        if (sufficientForReversal) {
            return returnNoOverdrafted();
        }

        BigDecimal balance = isDirectDebit
                ? accountBalance.getAvailableBalance()
                : accountBalance.getReserveBalance();

        BigDecimal overdraftedAmount = calculateOverdraft(balance,
                convertedReversedAmount, accountBalance, isDirectDebit);

        return returnOverdrafted(overdraftedAmount);
    }


    private OverdraftResult preDeduct(final AccountBalance accountBalance,
                           final BalanceImpact balanceImpact,
                           final BigDecimal convertedAmount,
                           final boolean isReversedTransaction) {
        boolean isDirectDebit = balanceImpact.isDirectDebit();
        boolean isReserveDebit = balanceImpact.isReserveDebit();

        if (isReversedTransaction && !balanceImpact.isInternal()) {
            OverdraftResult result = getOverdraftResult(accountBalance,
                    isDirectDebit, isReserveDebit,
                    convertedAmount);

            if (result.overdrafted()) {return result;}
        }

        if (isDirectDebit) {
            accountBalanceValidator.validateCanSubtractAvailableBalance(
                    accountBalance,
                    convertedAmount
            );

            accountBalance.deductAvailableBalance(convertedAmount);
            return returnNoOverdrafted();
        }else if (isReserveDebit) {
            accountBalanceValidator.validateCanSubtractReserveBalance(
                    accountBalance,
                    convertedAmount
            );

            accountBalance.deductReserve(convertedAmount);
            return returnNoOverdrafted();
        }

        throw new SystemMisusedException("Invalid balance direction.");
    }

    private BigDecimal getFinalBaseAmount(final BigDecimal fullBaseAmount,
                                          final BigDecimal overdraftedAmount,
                                          final FxRate fxRate,
                                          final FxDirection fxDirection) {
        //when transaction does not used any fx rate
        if (fxDirection == FxDirection.NONE) {
            return overdraftedAmount;
        }

        return currencyConversionService.getBaseAmountFromConvertedAmount(fxRate,
                overdraftedAmount,
                fxDirection, fxRate.getQuoteCurrency());
    }



    private void postAccount(final boolean isReversedTransaction,
                             final TransactionLine transactionLine,
                             final Transaction transaction,
                             final @Nullable Map<UUID, OverdraftedAccount> overdraftedAccounts) {
        Account account = transactionLine.getAccount();
        UUID accountId = account.getAccountId();
        AccountBalance accountBalance = account.getAccountBalance();
        BalanceImpact balanceImpact = transactionLine.getBalanceImpact();
        LedgerAccount ledgerAccount = transactionLine.getLedgerAccount();
        BigDecimal baseAmount = transactionLine.getAmount();
        BigDecimal finalBalanceApplyingAmount = transactionLine.getConvertedAmount();
        BigDecimal finalLedgerApplyingAmount = transactionLine.getConvertedAmount();
        TransactionLineDirection transactionLineDirection = transactionLine.getTransactionLineDirection();
        FxRate fxRate = transactionLine.getFxRate();
        LedgerType ledgerType = ledgerAccount.getLedgerType();

        boolean requiresBalanceDeduction = balanceImpact.isDeductingBalance();

        if (requiresBalanceDeduction) {
            if (!isReversedTransaction) {
                accountBalanceValidator.validateIsOverdrafted(accountBalance);
            }

            OverdraftResult result = preDeduct(accountBalance, balanceImpact,
                    finalBalanceApplyingAmount, isReversedTransaction);
            boolean isOverdrafted = result.overdrafted();


            if (isOverdrafted) {
                FxDirection fxDirection = transaction.getFxDirection();
                BigDecimal overdraftedAmount = result.overdraftedAmount();
                BigDecimal finalBaseAmount = getFinalBaseAmount(baseAmount, overdraftedAmount,
                        fxRate, fxDirection);






                internalOverdraftedAccountCreateService
                        .createOverdraftedAccount(account, finalBaseAmount, overdraftedAmount,
                                transaction, fxRate, fxDirection);
            }


        }else {
            OverdraftedAccount overdraftedAccountForRollback = isReversedTransaction
                    ? overdraftedAccounts.get(accountId)
                    : null;
            if (overdraftedAccountForRollback != null) {
                accountBalance.decreaseOverdraft(overdraftedAccountForRollback
                        .getTrackedConvertedAmount());

                OverdraftRollbackResult rollbackResult = internalOverdraftReversalService
                        .getSettledOverdraftAmount(overdraftedAccountForRollback,
                                finalBalanceApplyingAmount);


                finalBalanceApplyingAmount = rollbackResult.settledAmount();
                finalLedgerApplyingAmount = finalLedgerApplyingAmount
                        .add(rollbackResult.compensatedAmount());


            }


            applyAccountBalance(accountBalance, transactionLineDirection,
                    ledgerType, finalBalanceApplyingAmount);



        }

        accountBalance.ledgerApply(finalLedgerApplyingAmount,
                ledgerAccount, transactionLineDirection);




    }


    @Override
    public void postTransaction(final Transaction transaction) {
        TransactionStatusValidator.assertPost(transaction);
        transactionValidator.validateIsTransactionClosed(transaction);

        TransactionLineQueryData queryData = transactionLineFinder
                .getTransactionLinesByTransaction(transaction, REQUIRED_VALID_ACCOUNTS);

        List<TransactionLine> transactionLines = queryData.transactionLines()
                .stream().filter(l -> !ignoredBalanceImpact.contains(
                        l.getBalanceImpact())).toList();

        boolean isReversedTransaction = transaction.isReversedTransaction();

        if (transaction.isCardTransaction()) {cardTransactionValidator.validateIsCardTransactionValid(transaction);}
        if (!isReversedTransaction) {
            transactionValidator.validateIsCustomersSafeForTransaction(transactionLines);}

        Map<UUID, OverdraftedAccount> previousOverdraftedAccounts = isReversedTransaction
                ? overdraftedAccountFinder.findFromReversedTransaction(transactionLines,
                                        transaction.getReferencedReversedTransaction())
                : null;

        for (TransactionLine tl : transactionLines) {
            postAccount(isReversedTransaction, tl, transaction, previousOverdraftedAccounts);
        }

        transaction.changeTransactionStatus(TransactionStatus.POSTED);
        transaction.setPosted();


    }


}
