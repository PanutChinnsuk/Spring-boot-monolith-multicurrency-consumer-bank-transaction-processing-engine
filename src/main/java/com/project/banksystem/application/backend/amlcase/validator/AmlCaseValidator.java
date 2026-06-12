package com.project.banksystem.application.backend.amlcase.validator;

import com.project.banksystem.api.request.dto.amlcase.AmlCaseOpenDTO;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.core.enums.aml.AmlCaseStatus;
import com.project.banksystem.infrastructure.model.AmlCase;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.repository.AmlCaseRepository;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AmlCaseValidator {
    private final AmlCaseRepository amlCaseRepository;

    public AmlCaseValidator(AmlCaseRepository amlCaseRepository) {
        this.amlCaseRepository = amlCaseRepository;
    }


    public void validateAmlCaseNotClosed(final AmlCase amlCase) {
        if (amlCase.getAmlCaseStatus() == AmlCaseStatus.CLOSED) {
            throw new BusinessException("Aml case is closed.");
        }
    }

    public void validateCanOpen(final Customer customer,
                                final AmlCaseOpenDTO amlCaseOpenDTO) {
        if (amlCaseRepository.existsByCustomerIdAndLabel(customer.getCustomerId(), amlCaseOpenDTO.getLabel())) {
            throw new BusinessException("Case already exists.");
        }
    }

    public void validateAnyActiveAmlCase(final Set<UUID> customerIds) {
        if (amlCaseRepository.anyActiveAmlCase(customerIds, AmlCaseStatus.CLOSED)) {
            throw new BusinessException("One or more customer is not safe for transaction.");
        }
    }




}
