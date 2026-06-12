package com.project.banksystem.application.backend.moneytransactionsession.resultrecords;

import com.project.banksystem.infrastructure.model.User;

import java.math.BigDecimal;
import java.util.UUID;

public record ValidatedTransactionSessionRequestData(User currentUser) {
}
