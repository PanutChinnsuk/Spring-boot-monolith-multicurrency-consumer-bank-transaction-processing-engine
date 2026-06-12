package com.project.banksystem.application.backend.overdraft.internal.initial.body;

import com.project.banksystem.infrastructure.model.OverdraftPaybackRequest;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;

public record OverdraftPaybackResponseBody(OverdraftPaybackRequest overdraftPaybackRequest,
                                           OverdraftedAccount overdraftedAccount) {
}
