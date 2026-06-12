package com.project.banksystem.application.backend.overdraft.service;

import com.project.banksystem.application.backend.overdraft.body.OverdraftPaybackTemplate;
import com.project.banksystem.application.backend.overdraft.internal.initial.body.OverdraftPaybackResponseBody;

public interface OverdraftPaybackService {
    OverdraftPaybackResponseBody requestOverdraftPayback(OverdraftPaybackTemplate template);
}
