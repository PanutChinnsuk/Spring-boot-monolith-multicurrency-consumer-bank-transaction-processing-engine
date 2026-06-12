package com.project.banksystem.api.request.dto.internalaccount;

import com.project.banksystem.core.enums.account.AccountPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class InternalAccountCreateDTO {
    @NotNull
    private UUID accountId;

    @NotBlank
    private String branchCode;

    @NotNull
    private AccountPurpose accountPurpose;

    @NotBlank
    private String label;

    @NotBlank
    private String internalCode;

    public InternalAccountCreateDTO() {

    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public void setAccountPurpose(AccountPurpose accountPurpose) {
        this.accountPurpose = accountPurpose;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public AccountPurpose getAccountPurpose() {
        return accountPurpose;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getLabel() {
        return label;
    }

    public String getInternalCode() {
        return internalCode;
    }
}
