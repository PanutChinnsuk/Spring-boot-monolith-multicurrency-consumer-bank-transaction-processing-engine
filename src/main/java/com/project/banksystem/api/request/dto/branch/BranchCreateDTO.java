package com.project.banksystem.api.request.dto.branch;

import jakarta.validation.constraints.NotBlank;

public class BranchCreateDTO {
    @NotBlank
    private String branchName;

    @NotBlank
    private String branchCode;

    public BranchCreateDTO() {

    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getBranchName() {
        return branchName;
    }
}
