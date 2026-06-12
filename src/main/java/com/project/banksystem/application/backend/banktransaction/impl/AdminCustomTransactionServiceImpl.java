package com.project.banksystem.application.backend.banktransaction.impl;

import com.project.banksystem.api.request.dto.banktransaction.admin.data.CustomTransactionCreateDTO;
import com.project.banksystem.api.request.dto.banktransaction.admin.data.CustomTransactionLineCreateDTO;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionCreateBody;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.TransactionLineReversedContext;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.initial.TransactionInitializer;
import com.project.banksystem.application.backend.banktransaction.service.AdminCustomTransactionService;
import com.project.banksystem.application.backend.banktransaction.validator.BankTransactionValidator;
import com.project.banksystem.application.backend.fxrate.body.FxRatesContext;
import com.project.banksystem.application.backend.fxrate.builder.FxRatesContextBuilder;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.FxRate;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

record TransactionLineConvertedResult(List<TransactionLineCreateData> lines,
                                      Account[] nonBaseCurrencyAccounts) {}

@Service
public class AdminCustomTransactionServiceImpl implements AdminCustomTransactionService {
    private final TransactionInitializer transactionInitializer;
    private final AccountFinder accountFinder;
    private final FxRatesContextBuilder fxRatesContextBuilder;
    private final SystemConfig systemConfig;
    private final BankTransactionValidator bankTransactionValidator;

    public AdminCustomTransactionServiceImpl(TransactionInitializer transactionInitializer,
                                             AccountFinder accountFinder,
                                             FxRatesContextBuilder fxRatesContextBuilder,
                                             SystemConfig systemConfig,
                                             BankTransactionValidator bankTransactionValidator) {
        this.transactionInitializer = transactionInitializer;
        this.accountFinder = accountFinder;
        this.fxRatesContextBuilder = fxRatesContextBuilder;
        this.systemConfig = systemConfig;
        this.bankTransactionValidator = bankTransactionValidator;
    }

    private TransactionCreateData toTransactionCreateData(final CustomTransactionCreateDTO dto) {
        return TransactionCreateData.builder()
                .transactionPrincipal(dto.getTransactionPrincipal())
                .transactionType(dto.getTransactionType())
                .fxDirection(dto.getFxDirection())
                .build();
    }

    private TransactionLineConvertedResult toTransactionLineCreateDatas(final List<CustomTransactionLineCreateDTO> dtos) {

        Set<String> accountNumbers = dtos.stream()
                .map(CustomTransactionLineCreateDTO::getAccountNumber)
                .collect(Collectors.toSet());
        Map<String, Account> accounts = accountFinder.findOperationalByAccountNumberSet(accountNumbers)
                .stream().collect(Collectors.toMap(
                        Account::getAccountNumber,
                        Function.identity()));

        if (accounts.size() != accountNumbers.size()) {
            throw new NotFoundException("One or more account is not valid for custom transaction.");
        }

        Set<Account> nonBaseCurrencyAccounts = new HashSet<>();

        List<TransactionLineCreateData> lines = dtos.stream()
                .map(dto -> {
                    Account account = accounts.get(dto.getAccountNumber());
                    CurrencyCode currencyCode = account.getCurrencyCode();
                    if (currencyCode != systemConfig.getBaseCurrencyCurrencyCode()) {
                        nonBaseCurrencyAccounts.add(account);
                    }

                    return TransactionLineCreateData.builder()
                            .account(account)
                            .balanceImpact(dto.getBalanceImpact())
                            .ledgerCode(dto.getLedgerCode())
                            .amount(dto.getAmount())
                            .direction(dto.getTransactionLineDirection())
                            .build();
                }).toList();

        return new TransactionLineConvertedResult(lines, nonBaseCurrencyAccounts.toArray(new Account[0]));
    }

    private Map<CurrencyCode, FxRate> getFxRatesForContext(final Account[] accounts) {
        Map<CurrencyCode, FxRate> fxRatesForContext = fxRatesContextBuilder
                .getFxRatesForContext(accounts);

        return fxRatesForContext;
    }

    @Override
    public Transaction customTransaction(final CustomTransactionCreateDTO customTransactionCreateDTO,
                                         final List<CustomTransactionLineCreateDTO> customTransactionLineCreateDTOS) {
        bankTransactionValidator.validateCustomTransaction(customTransactionLineCreateDTOS);

        TransactionCreateData transactionData = toTransactionCreateData(customTransactionCreateDTO);
        TransactionLineConvertedResult result =  toTransactionLineCreateDatas(customTransactionLineCreateDTOS);

        List<TransactionLineCreateData> transactionLines = result.lines();

        TransactionCreateBody body = TransactionCreateBody.builder()
                .transactionCreateData(transactionData)
                .transactionLineCreateDatas(transactionLines)
                .build();

        Account[] nonBaseCurrencyAccounts = result.nonBaseCurrencyAccounts();
        Map<CurrencyCode, FxRate> fxRatesForContext = nonBaseCurrencyAccounts.length > 0
                ? getFxRatesForContext(result.nonBaseCurrencyAccounts())
                : null;

        return transactionInitializer.initializeTransaction(
                body,
                new FxRatesContext(fxRatesForContext),
                new TransactionLineReversedContext(null),
                systemConfig.getBaseCurrencyCurrencyCode()
        );
    }
}
