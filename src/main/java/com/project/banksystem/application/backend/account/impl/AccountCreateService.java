package com.project.banksystem.application.backend.account.impl;


import com.project.banksystem.api.request.dto.account.AccountCreateDTO;
import com.project.banksystem.api.request.dto.account.body.AccountBalanceCreateData;
import com.project.banksystem.api.request.dto.account.body.AccountCreateData;
import com.project.banksystem.application.backend.account.AccountManager;
import com.project.banksystem.application.backend.account.AccountNumberGenerator;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.accountbalance.impl.AccountBalanceCreateService;
import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AccountCreateService {
    private final AccountManager accountManager;
    private final CustomerFinder customerFinder;
    private final AccountBalanceCreateService accountBalanceCreateService;
    private final AccountValidator accountValidator;
    private final AccountNumberGenerator accountNumberGenerator;

    public AccountCreateService(AccountManager accountManager,
                                CustomerFinder customerFinder,
                                AccountBalanceCreateService accountBalanceCreateService,
                                AccountValidator accountValidator,
                                AccountNumberGenerator accountNumberGenerator) {
        this.accountManager = accountManager;
        this.customerFinder = customerFinder;
        this.accountBalanceCreateService = accountBalanceCreateService;
        this.accountValidator = accountValidator;
        this.accountNumberGenerator = accountNumberGenerator;
    }


    public Account createAccount(final AccountCreateDTO accountCreateDTO) {
        Customer customer = customerFinder.findOperationalById(accountCreateDTO.getCustomerId());
        AccountCreateData accountCreateData = accountCreateDTO.getAccountCreateData();
        AccountBalanceCreateData accountBalanceCreateData = accountCreateDTO.getAccountBalanceCreateData();




        Account account = Account.builder()
                .customer(customer)
                .accountNumber(accountNumberGenerator.generateAccountNumber())
                .accountType(accountCreateData.getAccountType())
                .currencyCode(accountCreateData.getCurrencyCode())
                .accountStatus(accountCreateData.getRequiredApproval()
                        ? AccountStatus.PENDING_APPROVAL
                        : AccountStatus.ACTIVE)
                .openedAt(accountCreateData.getOpenedAt())
                .builder();



        Account savedAccount = accountManager.save(account);



        accountBalanceCreateService.createAccountBalance(savedAccount,
                accountBalanceCreateData);

        return savedAccount;

    }



}
