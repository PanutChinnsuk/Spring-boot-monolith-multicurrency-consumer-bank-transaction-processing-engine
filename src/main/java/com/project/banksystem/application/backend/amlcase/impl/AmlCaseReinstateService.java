package com.project.banksystem.application.backend.amlcase.impl;

import com.project.banksystem.application.backend.amlcase.query.AmlCaseFinder;
import com.project.banksystem.application.backend.amlcase.validator.AmlCaseStatusValidator;
import com.project.banksystem.application.backend.amlcase.validator.AmlCaseValidator;
import com.project.banksystem.core.enums.aml.AmlCaseStatus;
import com.project.banksystem.infrastructure.model.AmlCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AmlCaseReinstateService {
    private final AmlCaseFinder amlCaseFinder;
    private final AmlCaseValidator amlCaseValidator;

    public AmlCaseReinstateService(AmlCaseFinder amlCaseFinder,
                                   AmlCaseValidator amlCaseValidator) {
        this.amlCaseFinder = amlCaseFinder;
        this.amlCaseValidator = amlCaseValidator;
    }

    public AmlCase reinstateAmlCase(final UUID amlCaseId) {
        AmlCase amlCase = amlCaseFinder.findById(amlCaseId);
        AmlCaseStatusValidator.assertReinstate(amlCase);

        amlCase.changeAmlCaseStatus(AmlCaseStatus.OPEN);
        amlCase.changeLastReinstate(LocalDateTime.now());

        return amlCase;
    }
}
