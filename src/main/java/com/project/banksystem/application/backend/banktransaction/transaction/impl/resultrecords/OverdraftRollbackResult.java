package com.project.banksystem.application.backend.banktransaction.transaction.impl.resultrecords;

import java.math.BigDecimal;

public record OverdraftRollbackResult(BigDecimal settledAmount,
                                      BigDecimal compensatedAmount) {
}
