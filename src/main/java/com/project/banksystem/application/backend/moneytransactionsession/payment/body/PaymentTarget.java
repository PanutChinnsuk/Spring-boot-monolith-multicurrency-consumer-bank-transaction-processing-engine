package com.project.banksystem.application.backend.moneytransactionsession.payment.body;

import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;

public record PaymentTarget(Card targetCard, Account targetAccount) {
}
