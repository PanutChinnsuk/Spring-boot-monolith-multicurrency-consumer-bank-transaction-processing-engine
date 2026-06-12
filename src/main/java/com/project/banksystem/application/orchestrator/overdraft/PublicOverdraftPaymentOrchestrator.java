package com.project.banksystem.application.orchestrator.overdraft;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.overdraft.body.OverdraftPaybackTemplate;
import com.project.banksystem.application.backend.overdraft.body.mapper.OverdraftPaybackTemplateMapper;
import com.project.banksystem.application.backend.overdraft.service.OverdraftPaybackService;
import com.project.banksystem.core.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublicOverdraftPaymentOrchestrator {
    private final OverdraftPaybackService overdraftPaybackService;
    private final OverdraftPaybackTemplateMapper overdraftPaybackTemplateMapper;

    public PublicOverdraftPaymentOrchestrator(OverdraftPaybackService overdraftPaybackService,
                                              OverdraftPaybackTemplateMapper overdraftPaybackTemplateMapper) {
        this.overdraftPaybackService = overdraftPaybackService;
        this.overdraftPaybackTemplateMapper = overdraftPaybackTemplateMapper;
    }


    public OverdraftPaybackTemplate getTemplate() {
        return overdraftPaybackTemplateMapper.publicPaybackTemplate();
    }

    @Transactional
    public Result payback() {
        overdraftPaybackService.requestOverdraftPayback(getTemplate());

        return DataResultBuilder.build("Successfully requested overdraft payback.");
    }



}
