package com.project.banksystem.application.backend.overdraft.impl.preset;

import com.project.banksystem.application.backend.internalaccount.internal.InternalAccountMapper;
import com.project.banksystem.application.backend.internalaccount.map.InternalAccountCodeMap;
import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackLineCreateData;
import com.project.banksystem.application.backend.overdraft.internal.builder.OverdraftPaybackLineDataBuilder;
import com.project.banksystem.core.enums.account.AccountPurpose;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackLineDirection;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRole;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class OverdraftPaybackLinePreset {
    private final InternalAccountMapper
            mapper;


    public OverdraftPaybackLinePreset(
            InternalAccountMapper internalAccountMapper) {
        this.mapper = internalAccountMapper;
    }



    public List<OverdraftPaybackLineCreateData> getDefaultPaybackLine(
            final Account principalPaybackAccount,
            final BigDecimal paybackAmount) {

        AccountPurpose bankLiquidity = AccountPurpose.BANK_LIQUIDITY;

        Map<String, Account> internalAccounts = mapper.fromTCB(bankLiquidity);
        String mainKey = InternalAccountCodeMap.fromTCB(bankLiquidity);

        List<OverdraftPaybackLineCreateData> lines = List.of(
                OverdraftPaybackLineDataBuilder.createLineData(principalPaybackAccount,
                        OverdraftPaybackLineDirection.PAYBACK,
                        OverdraftPaybackRole.PAYBACK_PRINCIPAL,
                        paybackAmount),
                OverdraftPaybackLineDataBuilder.createLineData(internalAccounts.get(mainKey),
                        OverdraftPaybackLineDirection.GAIN,
                        OverdraftPaybackRole.PAYBACK_COLLECTOR,
                        paybackAmount)
        );


        return lines;
    }

    public List<OverdraftPaybackLineCreateData> getDefaultOverpaidPaybackLine(
            final Account principalPaybackAccount,
            final BigDecimal finalNonConverted,
            final BigDecimal payingAmount) {

        AccountPurpose bank = AccountPurpose.BANK_LIQUIDITY;
        AccountPurpose clearing = AccountPurpose.OVERDRAFT_OVERPAID_CLEARING;

        Map<String, Account> internalAccounts = mapper.fromTCB(bank, clearing);
        String collectorKey = InternalAccountCodeMap.fromTCB(bank);
        String clearingKey = InternalAccountCodeMap.fromTCB(bank);

        List<OverdraftPaybackLineCreateData> lines = List.of(
                OverdraftPaybackLineDataBuilder.createLineData(principalPaybackAccount,
                        OverdraftPaybackLineDirection.PAYBACK,
                        OverdraftPaybackRole.PAYBACK_PRINCIPAL,
                        payingAmount),
                OverdraftPaybackLineDataBuilder.createLineData(internalAccounts.get(collectorKey),
                        OverdraftPaybackLineDirection.GAIN,
                        OverdraftPaybackRole.PAYBACK_COLLECTOR,
                        finalNonConverted),
                OverdraftPaybackLineDataBuilder.createLineData(internalAccounts.get(clearingKey),
                        OverdraftPaybackLineDirection.GAIN,
                        OverdraftPaybackRole.PAYBACK_OVERPAID_CLEARING,
                        payingAmount.subtract(finalNonConverted))
        );


        return lines;
    }
}
