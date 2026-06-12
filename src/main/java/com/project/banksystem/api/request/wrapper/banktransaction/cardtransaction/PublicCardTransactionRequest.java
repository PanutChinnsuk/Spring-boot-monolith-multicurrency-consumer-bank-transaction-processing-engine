package com.project.banksystem.api.request.wrapper.banktransaction.cardtransaction;

import com.project.banksystem.api.request.dto.banktransaction.publicuse.PublicCardTransactionDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class PublicCardTransactionRequest {
    @NotNull
    @Valid
    private PublicCardTransactionDTO publicCardTransactionDTO;

    public PublicCardTransactionRequest() {

    }

    public void setPublicCardTransactionDTO(PublicCardTransactionDTO publicCardTransactionDTO) {
        this.publicCardTransactionDTO = publicCardTransactionDTO;
    }

    public PublicCardTransactionDTO getPublicCardTransactionDTO() {
        return publicCardTransactionDTO;
    }
}
