package com.project.banksystem.application.backend.amlcase.impl;

import com.project.banksystem.application.backend.amlcase.query.AmlCaseFinder;
import com.project.banksystem.application.backend.amlcase.validator.AmlCaseStatusValidator;
import com.project.banksystem.application.backend.amlcase.validator.AmlCaseValidator;
import com.project.banksystem.core.enums.aml.AmlCaseStatus;
import com.project.banksystem.infrastructure.model.AmlCase;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class AmlCaseStatusService {
    private final AmlCaseValidator amlCaseValidator;
    private final AmlCaseFinder amlCaseFinder;

    public AmlCaseStatusService(AmlCaseValidator amlCaseValidator,
                                AmlCaseFinder amlCaseFinder) {
        this.amlCaseValidator = amlCaseValidator;
        this.amlCaseFinder = amlCaseFinder;
    }

    public AmlCase updateAmlCaseStatus(final UUID amlCaseId,
                                       final AmlCaseStatus amlCaseStatus) {
        AmlCase amlCase = amlCaseFinder.findNonClosedById(amlCaseId);
        AmlCaseStatusValidator.assertUpdateStatus(amlCase, amlCaseStatus);

        amlCase.changeAmlCaseStatus(amlCaseStatus);
        return amlCase;
    }
}
