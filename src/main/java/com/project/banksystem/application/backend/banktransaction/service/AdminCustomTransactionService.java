package com.project.banksystem.application.backend.banktransaction.service;

import com.project.banksystem.api.request.dto.banktransaction.admin.data.CustomTransactionCreateDTO;
import com.project.banksystem.api.request.dto.banktransaction.admin.data.CustomTransactionLineCreateDTO;
import com.project.banksystem.infrastructure.model.Transaction;

import java.util.List;

public interface AdminCustomTransactionService {
    Transaction customTransaction(CustomTransactionCreateDTO customTransactionCreateDTO,
                                         List<CustomTransactionLineCreateDTO> customTransactionLineCreateDTOS);
}
