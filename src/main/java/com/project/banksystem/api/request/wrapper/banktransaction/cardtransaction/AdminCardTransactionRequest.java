package com.project.banksystem.api.request.wrapper.banktransaction.cardtransaction;

import com.project.banksystem.api.request.dto.banktransaction.admin.AdminCardTransactionDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class AdminCardTransactionRequest {
    @NotNull
    @Valid
    private AdminCardTransactionDTO adminCardTransactionDTO;

    public AdminCardTransactionRequest() {

    }

    public void setAdminCardTransactionDTO(AdminCardTransactionDTO adminCardTransactionDTO) {
        this.adminCardTransactionDTO = adminCardTransactionDTO;
    }

    public AdminCardTransactionDTO getAdminCardTransactionDTO() {
        return adminCardTransactionDTO;
    }
}
