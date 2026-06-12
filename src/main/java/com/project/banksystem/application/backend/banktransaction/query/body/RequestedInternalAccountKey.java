package com.project.banksystem.application.backend.banktransaction.query.body;

import com.project.banksystem.core.enums.account.AccountPurpose;

public class RequestedInternalAccountKey {
    private String branchName;
    private AccountPurpose accountPurpose;

    public RequestedInternalAccountKey(String branchName,
                                       AccountPurpose accountPurpose) {
        this.branchName = branchName;
        this.accountPurpose = accountPurpose;
    }

    public String getBranchName() {
        return branchName;
    }

    public AccountPurpose getAccountPurpose() {
        return accountPurpose;
    }
}
