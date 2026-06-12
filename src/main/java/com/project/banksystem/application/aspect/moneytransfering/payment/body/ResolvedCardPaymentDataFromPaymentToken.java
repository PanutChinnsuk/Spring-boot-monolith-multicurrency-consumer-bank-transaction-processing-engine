package com.project.banksystem.application.aspect.moneytransfering.payment.body;

import java.math.BigDecimal;
import java.util.UUID;

public record ResolvedCardPaymentDataFromPaymentToken(UUID cardId, BigDecimal amount,
                                                      UUID targetCardId) {


}
