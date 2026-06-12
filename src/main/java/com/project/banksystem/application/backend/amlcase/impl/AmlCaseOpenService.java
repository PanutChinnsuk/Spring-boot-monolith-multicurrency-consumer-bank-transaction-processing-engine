package com.project.banksystem.application.backend.amlcase.impl;

import com.project.banksystem.api.request.dto.amlcase.AmlCaseOpenDTO;
import com.project.banksystem.application.backend.amlcase.AmlCaseManager;
import com.project.banksystem.application.backend.amlcase.validator.AmlCaseValidator;
import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.infrastructure.model.AmlCase;
import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.stereotype.Service;


@Service
public class AmlCaseOpenService {
    private final AmlCaseManager amlCaseManager;
    private final CustomerFinder customerFinder;
    private final AmlCaseValidator amlCaseValidator;

    public AmlCaseOpenService(AmlCaseManager amlCaseManager,
                              CustomerFinder customerFinder,
                              AmlCaseValidator amlCaseValidator) {
        this.amlCaseManager = amlCaseManager;
        this.customerFinder = customerFinder;
        this.amlCaseValidator = amlCaseValidator;
    }

    public AmlCase openCase(final AmlCaseOpenDTO amlCaseOpenDTO) {
        Customer customer = customerFinder.findNonTerminatedById(amlCaseOpenDTO.getCustomerId());

        amlCaseValidator.validateCanOpen(customer, amlCaseOpenDTO);

        return amlCaseManager.save(AmlCase.builder()
                .customer(customer)
                .label(amlCaseOpenDTO.getLabel())
                .build());
    }

}
