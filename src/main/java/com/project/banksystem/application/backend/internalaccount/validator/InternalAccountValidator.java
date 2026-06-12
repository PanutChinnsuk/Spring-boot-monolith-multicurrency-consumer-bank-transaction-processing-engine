package com.project.banksystem.application.backend.internalaccount.validator;

import com.project.banksystem.api.request.dto.internalaccount.InternalAccountCreateDTO;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.repository.InternalAccountRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InternalAccountValidator {
    private final InternalAccountRepository internalAccountRepository;

    public InternalAccountValidator(InternalAccountRepository internalAccountRepository) {
        this.internalAccountRepository = internalAccountRepository;
    }

    public void validateCanCreate(final InternalAccountCreateDTO internalAccountCreateDTO) {

        if (internalAccountRepository.alreadyExist(
                internalAccountCreateDTO.getBranchCode(),
                internalAccountCreateDTO.getAccountId(),
                internalAccountCreateDTO.getLabel(),
                internalAccountCreateDTO.getAccountPurpose(),
                internalAccountCreateDTO.getInternalCode()
        )) {
            throw new BusinessException("An internal account already exist within" +
                    " this branch.");
        }


    }


}
