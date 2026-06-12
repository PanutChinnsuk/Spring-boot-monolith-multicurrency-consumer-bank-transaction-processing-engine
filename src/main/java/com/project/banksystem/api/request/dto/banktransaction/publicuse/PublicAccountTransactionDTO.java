package com.project.banksystem.api.request.dto.banktransaction.publicuse;

import jakarta.validation.constraints.NotBlank;

public class PublicAccountTransactionDTO {
    @NotBlank
    private String targetAccountNumber;

    public PublicAccountTransactionDTO() {

    }

    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }
}
