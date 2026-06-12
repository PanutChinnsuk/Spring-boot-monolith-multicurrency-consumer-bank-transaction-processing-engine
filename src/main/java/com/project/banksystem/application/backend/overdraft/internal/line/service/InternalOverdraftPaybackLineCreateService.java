package com.project.banksystem.application.backend.overdraft.internal.line.service;

import com.project.banksystem.application.backend.currency.CurrencyConversionService;
import com.project.banksystem.application.backend.fxrate.query.FxRateFinder;
import com.project.banksystem.application.backend.ledgeraccount.map.LedgerMap;
import com.project.banksystem.application.backend.ledgeraccount.query.LedgerAccountFinder;
import com.project.banksystem.application.backend.overdraft.internal.account.validator.OverdraftedAccountValidator;
import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackLineCreateData;
import com.project.banksystem.application.backend.overdraft.internal.line.OverdraftPaybackLineManager;
import com.project.banksystem.application.backend.overdraft.internal.line.validator.OverdraftPaybackLineValidator;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.ledger.LedgerKey;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackLineDirection;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRole;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.*;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class InternalOverdraftPaybackLineCreateService {
    private final OverdraftPaybackLineManager overdraftPaybackLineManager;
    private final SystemConfig systemConfig;
    private final CurrencyConversionService currencyConversionService;
    private final OverdraftedAccountValidator overdraftedAccountValidator;
    private final OverdraftPaybackLineValidator overdraftPaybackLineValidator;
    private final LedgerAccountFinder ledgerAccountFinder;

    public InternalOverdraftPaybackLineCreateService(OverdraftPaybackLineManager overdraftPaybackLineManager,
                                                     SystemConfig systemConfig,
                                                     CurrencyConversionService currencyConversionService,
                                                     OverdraftedAccountValidator overdraftedAccountValidator,
                                                     OverdraftPaybackLineValidator overdraftPaybackLineValidator,
                                                     LedgerAccountFinder ledgerAccountFinder) {
        this.overdraftPaybackLineManager = overdraftPaybackLineManager;
        this.systemConfig = systemConfig;
        this.currencyConversionService = currencyConversionService;
        this.overdraftedAccountValidator = overdraftedAccountValidator;
        this.overdraftPaybackLineValidator = overdraftPaybackLineValidator;
        this.ledgerAccountFinder = ledgerAccountFinder;
    }

    private FxRate getFxRate(final OverdraftedAccount overdraftedAccount) {
        FxRate fxRate = overdraftedAccount.getFxRate();

        if (fxRate == null) {
           throw new SystemMisusedException("Required fx rate not found from overdrafted account.");
        }

        return fxRate;
    }

    private BigDecimal getTreasuryAmount(final BigDecimal monetaryAmount,
                                      final FxRate fxRate,
                                      final CurrencyCode overdraftedInCurrency,
                                         final FxDirection usedFxDirection) {
        BigDecimal finalAmount = monetaryAmount;

        if (fxRate != null) {
            finalAmount = currencyConversionService.getBaseAmountFromConvertedAmount(fxRate,
                    finalAmount, usedFxDirection, overdraftedInCurrency);
        }

        return finalAmount;
    }

    private LedgerAccount getLedgerAccount(final OverdraftedAccount overdraftedAccount) {
        boolean isBaseCurrencyOverdraft = overdraftedAccount
                .getCurrencyCode() == systemConfig.getBaseCurrencyCurrencyCode();

        LedgerKey key = isBaseCurrencyOverdraft
                ? LedgerKey.OVERDRAFT_PAYBACK
                : LedgerKey.FOREIGN_OVERDRAFT_PAYBACK;

        return ledgerAccountFinder.findByCode(LedgerMap.getLedgerCodeByKey(key));
    }

    private BigDecimal getFinalAmount(final BigDecimal treasuryAmount,
                                                 final OverdraftPaybackLineDirection direction,
                                                 final BigDecimal baseOwnedAmount) {
        BigDecimal finalAmount = treasuryAmount;

        if (direction.isGainSide() && treasuryAmount.compareTo(baseOwnedAmount) > 0) {
            finalAmount = baseOwnedAmount;
        }

        return finalAmount;
    }

    public void createPaybackLines(final OverdraftedAccount overdraftedAccount,
                                   final OverdraftPaybackRequest overdraftPaybackRequest,
                                   final List<OverdraftPaybackLineCreateData> createDatas) {
        overdraftedAccountValidator.validateMatchedLocalCurrency(overdraftedAccount);
        overdraftPaybackLineValidator.validateOnlyOnePaybackPrincipal(createDatas);

        Map<OverdraftPaybackLineDirection, BigDecimal> netMap = overdraftPaybackLineValidator
                .validateNet(createDatas);
        overdraftPaybackLineValidator.validateTotalPaybackAndGainMatched(netMap);
        overdraftPaybackLineValidator.validateCircularPattern(createDatas);

        UUID mainPaybackPrincipalAccountId = overdraftedAccount.getAccount().getAccountId();
        CurrencyCode overdraftedInCurrency = overdraftedAccount.getCurrencyCode();

        BigDecimal baseOwnedAmount = overdraftedAccount.getBaseAmount();

        FxRate fxRate = overdraftedInCurrency == systemConfig.getBaseCurrencyCurrencyCode()
                ? null
                : getFxRate(overdraftedAccount);

        LedgerAccount ledgerAccount = getLedgerAccount(overdraftedAccount);
        FxDirection usedFxDirection = overdraftedAccount.getOnFxDirection();


        List<OverdraftPaybackLine> lines = createDatas.stream()
                .map(cd -> {
                    Account account = cd.getAccount();
                    UUID accountId = account.getAccountId();
                    OverdraftPaybackLineDirection direction = cd.getOverdraftPaybackLineDirection();
                    BigDecimal monetaryAmount = cd.getPaybackAmount();
                    BigDecimal treasuryConvertedAmount = getTreasuryAmount(monetaryAmount,
                            fxRate, overdraftedInCurrency, usedFxDirection);
                    BigDecimal finalAmount = getFinalAmount(treasuryConvertedAmount, direction,
                            baseOwnedAmount);

                    OverdraftPaybackRole overdraftPaybackRole = cd.getOverdraftPaybackRole();

                    if (overdraftPaybackRole == OverdraftPaybackRole.PAYBACK_PRINCIPAL) {
                        finalAmount = monetaryAmount;
                        overdraftPaybackLineValidator.validatePrincipalIdPaybackMatched(
                                mainPaybackPrincipalAccountId, accountId);
                    }

                    return OverdraftPaybackLine.builder()
                            .account(account)
                            .ledgerAccount(ledgerAccount)
                            .monetaryAmount(monetaryAmount)
                            .convertedAmount(treasuryConvertedAmount)
                            .finalAmount(finalAmount)
                            .overdraftPaybackLineDirection(direction)
                            .overdraftPaybackRole(overdraftPaybackRole)
                            .overdraftPaybackRequest(overdraftPaybackRequest)
                            .build();
                }).toList();

        overdraftPaybackLineValidator.validateTotalGainNotExceeded(lines, baseOwnedAmount);


        overdraftPaybackLineManager.saveAll(lines);
    }
}
