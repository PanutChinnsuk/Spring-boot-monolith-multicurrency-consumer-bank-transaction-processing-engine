package com.project.banksystem.application.backend.banktransaction.query;


import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.banktransaction.body.TransferTemplate;
import com.project.banksystem.application.backend.internalaccount.query.InternalAccountFinder;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TransactionPaymentMapper {
    private final AccountFinder accountFinder;
    private final InternalAccountFinder internalAccountFinder;


    public TransactionPaymentMapper(AccountFinder accountFinder,
                                    InternalAccountFinder internalAccountFinder) {
        this.accountFinder = accountFinder;
        this.internalAccountFinder = internalAccountFinder;
    }

    public TransferTemplate publicTransferTemplate() {
        return TransferTemplate.builder().build();
    }


    public TransferTemplate adminTransferTemplate(final String fromAccountNumber,
                                                  final String toAccountNumber,
                                                  final BigDecimal baseAmount) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new BusinessException("From and to account numbers should be different.");
        }

        Set<String> accountNumbers = Set.of(fromAccountNumber, toAccountNumber);


        Map<String, Account> accounts = accountFinder.findOperationalByAccountNumberSet(Set.of(
                fromAccountNumber, toAccountNumber
        )).stream().collect(Collectors.toMap(
                Account::getAccountNumber,
                a -> a
        ));

        if (accounts.size() != accountNumbers.size()) {
            throw new IllegalStateException("One or more account is not valid for transfer.");
        }

        return TransferTemplate.builder()
                .fromAccount(accounts.get(fromAccountNumber))
                .toAccount(accounts.get(toAccountNumber))
                .baseAmount(baseAmount)
                .build();
    }



    public Map<String, Account> getInternalAccountsAsMap(final Set<String> internalCodes) {
        return internalAccountFinder.getOperationalByBaseCurrencyByInternalCodes(internalCodes);
    }




}
