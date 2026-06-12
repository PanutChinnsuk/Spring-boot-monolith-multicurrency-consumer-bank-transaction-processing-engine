package com.project.banksystem.api.request.wrapper.banktransaction.accounttransaction;

import com.project.banksystem.api.request.dto.banktransaction.admin.AdminAccountTransactionDTO;
import com.project.banksystem.api.request.dto.banktransaction.publicuse.PublicAccountTransactionDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class AdminAccountTransactionRequest {
    @Valid
    @NotNull
    private AdminAccountTransactionDTO adminAccountTransactionDTO;

    public AdminAccountTransactionRequest() {}


    public void setAdminAccountTransactionDTO(AdminAccountTransactionDTO adminAccountTransactionDTO) {
        this.adminAccountTransactionDTO = adminAccountTransactionDTO;
    }

    public AdminAccountTransactionDTO getAdminAccountTransactionDTO() {
        return adminAccountTransactionDTO;
    }
}
