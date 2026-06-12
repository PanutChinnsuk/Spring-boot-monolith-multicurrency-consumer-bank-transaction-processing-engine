package com.project.banksystem.api.request.wrapper.banktransaction.accounttransaction;

import com.project.banksystem.api.request.dto.banktransaction.admin.data.CustomTransactionCreateDTO;
import com.project.banksystem.api.request.dto.banktransaction.admin.data.CustomTransactionLineCreateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AdminCustomAccountTransactionRequest {
    @Valid
    @NotNull
    private CustomTransactionCreateDTO customTransactionCreateDTO;

    @NotNull
    @NotEmpty
    private List<@Valid CustomTransactionLineCreateDTO> customTransactionLineCreateDTOS;

    public AdminCustomAccountTransactionRequest() {

    }

    public void setCustomTransactionCreateDTO(CustomTransactionCreateDTO customTransactionCreateDTO) {
        this.customTransactionCreateDTO = customTransactionCreateDTO;
    }

    public void setCustomTransactionLineCreateDTOS(List<CustomTransactionLineCreateDTO> customTransactionLineCreateDTOS) {
        this.customTransactionLineCreateDTOS = customTransactionLineCreateDTOS;
    }

    public CustomTransactionCreateDTO getCustomTransactionCreateDTO() {
        return customTransactionCreateDTO;
    }

    public List<CustomTransactionLineCreateDTO> getCustomTransactionLineCreateDTOS() {
        return customTransactionLineCreateDTOS;
    }
}
