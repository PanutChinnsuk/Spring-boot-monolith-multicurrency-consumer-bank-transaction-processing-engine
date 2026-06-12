package com.project.banksystem.application.backend.internalaccount.internal;

import com.project.banksystem.application.backend.banktransaction.query.TransactionPaymentMapper;
import com.project.banksystem.application.backend.internalaccount.map.InternalAccountCodeMap;
import com.project.banksystem.core.enums.account.AccountPurpose;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class InternalAccountMapper {
    private final TransactionPaymentMapper transactionPaymentMapper;

    public InternalAccountMapper(TransactionPaymentMapper transactionPaymentMapper) {
        this.transactionPaymentMapper = transactionPaymentMapper;
    }


    public Map<String, Account> fromTCB(final AccountPurpose... accountPurposes
                                                                                                  ) {
        return transactionPaymentMapper.getInternalAccountsAsMap(
                InternalAccountCodeMap.fromTCB(accountPurposes));

    }


    public Account getMainAccountFromTCB() {
        AccountPurpose bank = AccountPurpose.BANK_LIQUIDITY;

        return fromTCB(bank).get(InternalAccountCodeMap.fromTCB(bank));
    }
}
