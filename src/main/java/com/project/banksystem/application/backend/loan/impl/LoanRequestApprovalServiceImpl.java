package com.project.banksystem.application.backend.loan.impl;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.currency.CurrencyConversionService;
import com.project.banksystem.application.backend.fxrate.query.FxRateFinder;
import com.project.banksystem.application.backend.internalaccount.internal.InternalAccountMapper;
import com.project.banksystem.application.backend.internalaccount.map.InternalAccountCodeMap;
import com.project.banksystem.application.backend.internalaccount.query.InternalAccountFinder;
import com.project.banksystem.application.backend.loan.loanschedule.internal.InternalLoanScheduleCreateService;
import com.project.banksystem.application.backend.loan.request.query.LoanRequestFinder;
import com.project.banksystem.application.backend.loan.request.service.LoanRequestService;
import com.project.banksystem.application.backend.loan.service.InternalLoanCreateService;
import com.project.banksystem.application.backend.loan.service.LoanRequestApprovalService;
import com.project.banksystem.application.backend.loan.validator.LoanRequestStatusValidator;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.account.AccountPurpose;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.loan.LoanRequestStatus;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
public class LoanRequestApprovalServiceImpl implements LoanRequestApprovalService {
    private final LoanRequestFinder loanRequestFinder;
    private final AccountValidator accountValidator;
    private final InternalLoanCreateService internalLoanCreateService;
    private final InternalLoanScheduleCreateService internalLoanScheduleCreateService;
    private final LoanRequestStatusValidator loanRequestStatusValidator;
    private final UserFinder userFinder;
    private final InternalAccountMapper internalAccountMapper;
    private final SystemConfig systemConfig;
    private final FxRateFinder fxRateFinder;
    private final CurrencyConversionService currencyConversionService;

    public LoanRequestApprovalServiceImpl(LoanRequestFinder loanRequestFinder,
                                          AccountValidator accountValidator,
                                          InternalLoanCreateService internalLoanCreateService,
                                          InternalLoanScheduleCreateService internalLoanScheduleCreateService,
                                          LoanRequestStatusValidator loanRequestStatusValidator,
                                          UserFinder userFinder,
                                          InternalAccountMapper internalAccountMapper,
                                          SystemConfig systemConfig,
                                          FxRateFinder fxRateFinder,
                                          CurrencyConversionService currencyConversionService) {
        this.loanRequestFinder = loanRequestFinder;
        this.accountValidator = accountValidator;
        this.internalLoanCreateService = internalLoanCreateService;
        this.internalLoanScheduleCreateService = internalLoanScheduleCreateService;
        this.loanRequestStatusValidator = loanRequestStatusValidator;
        this.userFinder = userFinder;
        this.internalAccountMapper = internalAccountMapper;
        this.systemConfig = systemConfig;
        this.fxRateFinder = fxRateFinder;
        this.currencyConversionService = currencyConversionService;
    }

    private final FxDirection conversionFxDirection = FxDirection.ASK;

    private void applyAccountBalances(final Account account,
                                      final LoanRequest loanRequest,
                                      final FxRate fxRate,
                                      final CurrencyCode targetCurrencyCode,
                                      final FxDirection usedFxDirection) {
        AccountPurpose fromBank = AccountPurpose.BANK_LIQUIDITY;

        Map<String, Account> internalAccounts = internalAccountMapper.fromTCB(fromBank);
        Account internalBank = internalAccounts.get(InternalAccountCodeMap
                .fromTCB(fromBank));
        AccountBalance bankBalance = internalBank.getAccountBalance();

        BigDecimal amount = loanRequest.getRequestedPrincipal();
        if (!bankBalance.isAccountBalanceSufficientFor(amount)) {
            throw new BusinessException("Internal bank account does not have " +
                    "sufficient fund for loan approval.");
        }

        if (account.getAccountId().equals(internalBank.getAccountId())) {
            throw new SystemMisusedException("Cannot use main bank liquidity for " +
                    "loan request.");
        }

        BigDecimal finalBankDeductAmount = amount;

        if (fxRate != null) {
            finalBankDeductAmount = currencyConversionService
                    .getBaseAmountFromConvertedAmount(fxRate, finalBankDeductAmount,
                            usedFxDirection, targetCurrencyCode);
        }

        AccountBalance accountBalance = account.getAccountBalance();
        accountBalance.increaseBothAvailableAndLedger(amount);
        bankBalance.deductBothAvailableAndLedger(finalBankDeductAmount);
    }


    @Override
    public Loan approveLoanRequest(final UUID loanRequestId) {
        LoanRequest loanRequest = loanRequestFinder.findForApprove(loanRequestId);
        loanRequestStatusValidator.assertPending(loanRequest);

        Account account = loanRequest.getAccount();
        accountValidator.validateAccountOperational(account);

        CurrencyCode baseCurrencyCode = systemConfig.getBaseCurrencyCurrencyCode();
        CurrencyCode accountCurrencyCode = account.getCurrencyCode();

        FxRate fxRate = account.getCurrencyCode().equals(baseCurrencyCode)
                ? null
                : fxRateFinder.findLatest(accountCurrencyCode);

        LocalDate startDate = LocalDate.now();
        Loan loan = internalLoanCreateService.createLoan(loanRequest, startDate, fxRate,
                conversionFxDirection);
        internalLoanScheduleCreateService.createLoanSchedules(loan, startDate);

        applyAccountBalances(account, loanRequest, fxRate, accountCurrencyCode,
                conversionFxDirection);
        loanRequest.changeLoanRequestStatus(LoanRequestStatus.APPROVED);


        User currentUser = userFinder.findById(CurrentUser.getCurrentUserId());
        loanRequest.setReviewedUser(currentUser);
        loanRequest.setApprovedLoan(loan);


        return loan;
    }

    @Override
    public LoanRequest rejectLoanRequest(final UUID loanRequestId) {
        LoanRequest loanRequest = loanRequestFinder.findForApprove(loanRequestId);
        loanRequestStatusValidator.assertPending(loanRequest);

        loanRequest.changeLoanRequestStatus(LoanRequestStatus.REJECTED);

        return loanRequest;
    }
}
