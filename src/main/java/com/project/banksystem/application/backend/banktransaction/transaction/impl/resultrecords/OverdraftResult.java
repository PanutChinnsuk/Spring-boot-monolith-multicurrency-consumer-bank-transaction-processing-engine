package com.project.banksystem.application.backend.banktransaction.transaction.impl.resultrecords;

import java.math.BigDecimal;

public record OverdraftResult(boolean overdrafted,
                              BigDecimal overdraftedAmount) {
}
