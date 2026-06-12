package com.project.banksystem.application.backend.loan.loanpayment.impl;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.aspect.moneytransfering.loan.anno.clarification.LoanPaybacKTransactionData;
import com.project.banksystem.application.aspect.moneytransfering.loan.anno.methodtrigger.ConsumeLoanPaymentSession;
import com.project.banksystem.application.aspect.moneytransfering.payment.anno.methodtrigger.ConsumePaymentSession;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.accountbalance.validator.AccountBalanceValidator;
import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.currency.CurrencyConversionService;
import com.project.banksystem.application.backend.internalaccount.internal.InternalAccountMapper;
import com.project.banksystem.application.backend.loan.LoanInterestCalculator;
import com.project.banksystem.application.backend.loan.LoanManager;
import com.project.banksystem.application.backend.loan.loanpayment.body.LoanPaymentTemplate;
import com.project.banksystem.application.backend.loan.loanpayment.service.LoanPaymentService;
import com.project.banksystem.application.backend.loan.loanschedule.query.LoanScheduleFinder;
import com.project.banksystem.application.backend.loan.validator.LoanPaymentValidator;
import com.project.banksystem.application.backend.loan.validator.LoanValidator;
import com.project.banksystem.core.enums.account.AccountPurpose;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.loan.LoanStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {
    private final LoanManager loanManager;
    private final LoanPaymentValidator loanPaymentValidator;
    private final AccountValidator accountValidator;
    private final AccountFinder accountFinder;
    private final LoanValidator loanValidator;
    private final LoanScheduleFinder loanScheduleFinder;
    private final LoanInterestCalculator loanInterestCalculator;
    private final AccountBalanceValidator accountBalanceValidator;
    private final CurrencyConversionService currencyConversionService;
    private final InternalAccountMapper internalAccountMapper;

    public LoanPaymentServiceImpl(LoanManager loanManager,
                                  LoanPaymentValidator loanPaymentValidator,
                                  AccountValidator accountValidator,
                                  AccountFinder accountFinder,
                                  LoanValidator loanValidator,
                                  LoanScheduleFinder loanScheduleFinder,
                                  LoanInterestCalculator loanInterestCalculator,
                                  AccountBalanceValidator accountBalanceValidator,
                                  CurrencyConversionService currencyConversionService,
                                  InternalAccountMapper internalAccountMapper) {
        this.loanManager = loanManager;
        this.loanPaymentValidator = loanPaymentValidator;
        this.accountValidator = accountValidator;
        this.accountFinder = accountFinder;
        this.loanValidator = loanValidator;
        this.loanScheduleFinder = loanScheduleFinder;
        this.loanInterestCalculator = loanInterestCalculator;
        this.accountBalanceValidator = accountBalanceValidator;
        this.currencyConversionService = currencyConversionService;
        this.internalAccountMapper = internalAccountMapper;
    }

    private BigDecimal getTotalDueAmount(final LoanSchedule loanSchedule,
                                         final LocalDate startDate,
                                         final BigDecimal partialInterestRate) {
        LocalDate endDate = LocalDate.now();

        long interestLength = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal interest = partialInterestRate.multiply(
                BigDecimal.valueOf(interestLength));

        BigDecimal paidAmount = loanSchedule.getPaidBackAmount();
        BigDecimal dueAmount = loanSchedule.getAmountDue().add(interest);
        BigDecimal total = dueAmount.subtract(paidAmount);

        if (paidAmount.compareTo(dueAmount) > 0) {
            throw new SystemMisusedException("Paid amount exceeded due amount.");
        }

        return total;
    }

    private void applyAccountBalance(final AccountBalance accountBalance,
                                     final BigDecimal amount) {
        accountBalance.deductBothAvailableAndLedger(amount);
    }

    private void refund(final AccountBalance accountBalance,
                                     final BigDecimal amount) {
        accountBalance.increaseBothAvailableAndLedger(amount);
    }

    private List<LoanSchedule> getUnpaidList(final List<LoanSchedule> unfiltered,
                                             final int listSizeLimit) {
        return unfiltered.stream()
                .filter(s -> !s.getPaid())
                .limit(listSizeLimit)
                .toList();
    }

    private void finalizeLoan(final List<LoanSchedule> filteredUnpaid,
                              final Loan loan) {
        boolean loanCleared = filteredUnpaid.isEmpty()
                ? true
                : false;

        if (!loanCleared) {
            loan.changeNextDueDate(filteredUnpaid.getFirst().getDueDate());
        }else {
            loan.changeLoanStatus(LoanStatus.CLOSED);
        }

    }

    private void settleSchedule(final LoanSchedule ls,
                                final BigDecimal totalDueAmount) {
        ls.paidSchedule();
        ls.increasePaidbackAmount(totalDueAmount);
    }


    private void settleBankAccountBalance(final Account bankAccount,
                                          final FxRate fxRate,
                                          final BigDecimal totalPaid,
                                          final FxDirection usedDirection) {
        BigDecimal finalTotalPaid = totalPaid;

        if (fxRate != null) {
            finalTotalPaid = currencyConversionService.getBaseAmountFromConvertedAmount(
                    fxRate, totalPaid, usedDirection, bankAccount.getCurrencyCode());
        }

        bankAccount.getAccountBalance().increaseBothAvailableAndLedger(finalTotalPaid);
    }

    @Override
    @ConsumeLoanPaymentSession
    public void payLoan(@LoanPaybacKTransactionData final LoanPaymentTemplate template) {
        Account account = template.getAccount();
        Loan loan = template.getLoan();
        BigDecimal amount = template.getAmount();

        loanValidator.validateLoanClosed(loan);
        if (!CurrentUser.isUserSystemAdministrator()) {
            loanPaymentValidator.validateLoanOwnership(loan, account);
        }

        AccountBalance accountBalance = account.getAccountBalance();
        accountBalanceValidator.validateCanSubtractAvailableBalance(accountBalance, amount);

        List<LoanSchedule> loanSchedules = loanScheduleFinder.findForPayment(loan)
                .stream()
                .sorted(Comparator.comparing(s -> s.getDueDate()))
                .toList();

        List<LoanSchedule> filteredSchedules = getUnpaidList(loanSchedules,
                loanSchedules.size());

        LocalDate startDate = loan.getStartsAt();
        applyAccountBalance(accountBalance, amount);

        BigDecimal payingAmount = amount;
        int currencyScale = account.getCurrencyCode().getCurrencyScale();

        FxRate usedFxRate = loan.getUsedFxRate();
        FxDirection usedFxDirection = loan.getUsedFxDirection();
        BigDecimal totalPaid = BigDecimal.ZERO;

        Account bankAccount = internalAccountMapper.getMainAccountFromTCB();

        for (LoanSchedule ls : filteredSchedules) {
            BigDecimal usedInterestRate = ls.getPartialInterest();
            BigDecimal totalDueAmount = getTotalDueAmount(ls, startDate, usedInterestRate);

            if (payingAmount.compareTo(totalDueAmount) == 0) {
                settleSchedule(ls, totalDueAmount);
                payingAmount = payingAmount.subtract(totalDueAmount);
                totalPaid = totalPaid.add(totalDueAmount);

                break;
            }

            if (payingAmount.compareTo(totalDueAmount) < 0) {
                ls.increasePaidbackAmount(payingAmount);

                BigDecimal currentPartialFromPrincipal =
                        ls.getPartialFromPrincipal()
                                .subtract(payingAmount);

                BigDecimal newPartialRateAmount = loanInterestCalculator.
                        getDailyInterest(currentPartialFromPrincipal, loan.getInterestRate(), currencyScale);

                ls.changePartialInterest(newPartialRateAmount);
                totalPaid = totalPaid.add(payingAmount);
                payingAmount = payingAmount.subtract(payingAmount);

                break;
            }

            if (payingAmount.compareTo(totalDueAmount) > 0) {
                totalPaid = totalPaid.add(totalDueAmount);
                payingAmount = payingAmount.subtract(totalDueAmount);

                settleSchedule(ls, totalDueAmount);


            }


        }

        if (payingAmount.signum() > 0) {
            refund(accountBalance, payingAmount);
        }

        settleBankAccountBalance(bankAccount, usedFxRate, totalPaid, usedFxDirection);
        finalizeLoan(getUnpaidList(filteredSchedules, 1), loan);


    }
}
