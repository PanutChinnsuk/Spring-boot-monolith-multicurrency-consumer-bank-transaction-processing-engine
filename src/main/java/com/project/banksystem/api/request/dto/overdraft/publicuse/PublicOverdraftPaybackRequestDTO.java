package com.project.banksystem.api.request.dto.overdraft.publicuse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public class PublicOverdraftPaybackRequestDTO {
    @NotNull
    private UUID overdraftedAccountId;


    public PublicOverdraftPaybackRequestDTO() {

    }

    public void setOverdraftedAccountId(UUID overdraftedAccountId) {
        this.overdraftedAccountId = overdraftedAccountId;
    }

    public UUID getOverdraftedAccountId() {
        return overdraftedAccountId;
    }
}
