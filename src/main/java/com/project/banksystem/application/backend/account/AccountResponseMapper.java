package com.project.banksystem.application.backend.account;

import com.project.banksystem.api.response.read.adminres.account.AccountAdminResponse;
import com.project.banksystem.api.response.read.adminres.transaction.body.body.TransactionLineAccountAdminSummary;
import com.project.banksystem.application.backend.accountbalance.AccountBalanceResponseMapper;
import com.project.banksystem.application.backend.customer.CustomerResponseMapper;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.AccountBalance;
import com.project.banksystem.infrastructure.model.Customer;

public final class AccountResponseMapper {
    private AccountResponseMapper() {

    }

    public static final TransactionLineAccountAdminSummary
    toTransactionLineAccountAdminSummary(final Account account) {
        return TransactionLineAccountAdminSummary.builder()
                .accountId(account.getAccountId())
                .accountType(account.getAccountType())
                .currency(account.getCurrencyCode())
                .build();
    }


    public static final AccountAdminResponse toAdminResponse(final Account account) {
        AccountBalance accountBalance = account.getAccountBalance();
        Customer customer = account.getCustomer();

        return AccountAdminResponse.builder()
                .accountId(account.getAccountId())

                .accountBalanceAdminSummary(AccountBalanceResponseMapper.
                        toAccountBalanceAdminSummary(accountBalance))

                .customerAdminSummary(CustomerResponseMapper
                        .toCustomerAdminSummary(customer))

                .accountStatus(account.getAccountStatus())
                .accountType(account.getAccountType())
                .currency(account.getCurrencyCode())
                .openedAt(account.getOpenedAt())
                .build();
    }


}
