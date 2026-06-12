package com.project.banksystem.api.request.dto.card;

import java.util.UUID;

public class CardCreateDTO {
    private UUID accountId;


    public CardCreateDTO() {

    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public UUID getAccountId() {
        return accountId;
    }
}
