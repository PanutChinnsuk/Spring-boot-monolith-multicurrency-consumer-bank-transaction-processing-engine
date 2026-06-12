package com.project.banksystem.application.backend.overdraft.internal.account.service;

import com.project.banksystem.application.backend.banktransaction.transaction.impl.resultrecords.OverdraftRollbackResult;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftedAccountStatus;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InternalOverdraftReversalService {


    private BigDecimal settle(final BigDecimal reversingAmount,
                              final BigDecimal debt) {
        if (reversingAmount.compareTo(debt) < 0) {
            throw new IllegalStateException("Reversing amount can't cover debt amount.");
        }

        return reversingAmount.subtract(debt);
    }

    public OverdraftRollbackResult getSettledOverdraftAmount(final OverdraftedAccount overdraftedAccount,
                                                             final BigDecimal reversingAmount) {
        boolean isChanged = overdraftedAccount.isTrackedChanged();
        BigDecimal totalDebt = overdraftedAccount.getConvertedAmount();
        BigDecimal currentDebt = overdraftedAccount.getTrackedConvertedAmount();


        BigDecimal refund = overdraftedAccount.getConvertedAmount().subtract(currentDebt);


        if (refund.compareTo(totalDebt) > 0) {
            throw new IllegalStateException("Refund exceeded original debt amount.");
        }

        BigDecimal settled = settle(reversingAmount, totalDebt).add(refund);

        if (isChanged) {
            overdraftedAccount.changeStatus(OverdraftedAccountStatus.REVERSAL_COMPENSATED);
        }else {
            overdraftedAccount.changeStatus(OverdraftedAccountStatus.REVERSAL_CLOSED);
        }


        return new OverdraftRollbackResult(settled, refund);
    }
}
