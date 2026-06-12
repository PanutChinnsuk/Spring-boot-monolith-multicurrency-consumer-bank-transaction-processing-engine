package com.project.banksystem.api.request.dto.banktransaction.publicuse;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PublicCardTransactionDTO {
    @NotNull
    private UUID targetCardId;

    public PublicCardTransactionDTO(UUID targetCardId) {
        this.targetCardId = targetCardId;
    }


    public void setTargetCardId(UUID targetCardId) {
        this.targetCardId = targetCardId;
    }

    public UUID getTargetCardId() {
        return targetCardId;
    }
}
