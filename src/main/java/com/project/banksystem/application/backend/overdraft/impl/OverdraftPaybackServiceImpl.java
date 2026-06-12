package com.project.banksystem.application.backend.overdraft.impl;

import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.backend.overdraft.body.OverdraftPaybackTemplate;
import com.project.banksystem.application.backend.overdraft.impl.preset.OverdraftPaybackLinePreset;
import com.project.banksystem.application.backend.overdraft.internal.account.query.OverdraftedAccountFinder;
import com.project.banksystem.application.backend.overdraft.internal.body.OverdraftPaybackCreateBody;
import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackLineCreateData;
import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackRequestCreateData;
import com.project.banksystem.application.backend.overdraft.internal.builder.OverdraftPaybackRequestDataBuilder;
import com.project.banksystem.application.backend.overdraft.internal.initial.OverdraftPaybackInitializer;
import com.project.banksystem.application.backend.overdraft.internal.initial.body.OverdraftPaybackResponseBody;
import com.project.banksystem.application.backend.overdraft.service.OverdraftPaybackService;
import com.project.banksystem.application.aspect.moneytransfering.overdraft.anno.clarification.OverdraftPaybacKTransactionData;
import com.project.banksystem.application.aspect.moneytransfering.overdraft.anno.methodtrigger.ConsumeOverdraftPaymentSession;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OverdraftPaybackServiceImpl implements OverdraftPaybackService {
    private final OverdraftedAccountFinder overdraftedAccountFinder;
    private final OverdraftPaybackInitializer overdraftPaybackInitializer;
    private final OverdraftPaybackLinePreset overdraftPaybackLinePreset;



    public OverdraftPaybackServiceImpl(OverdraftPaybackInitializer overdraftPaybackInitializer,
                                       OverdraftedAccountFinder overdraftedAccountFinder,
                                       OverdraftPaybackLinePreset overdraftPaybackLinePreset) {
        this.overdraftPaybackInitializer = overdraftPaybackInitializer;
        this.overdraftedAccountFinder = overdraftedAccountFinder;
        this.overdraftPaybackLinePreset = overdraftPaybackLinePreset;
    }

    private List<OverdraftPaybackLineCreateData> handleOverpaidLines(final Account usingAccount,
                                                                     final BigDecimal appliableAmount,
                                                                     final BigDecimal payingAmount) {
        return overdraftPaybackLinePreset
                .getDefaultOverpaidPaybackLine(usingAccount, appliableAmount, payingAmount);
    }

    private List<OverdraftPaybackLineCreateData> handleNonOverpaidLines(final Account usingAccount,
                                                                     final BigDecimal payingAmount) {
        return overdraftPaybackLinePreset
                .getDefaultPaybackLine(usingAccount, payingAmount);
    }
    private List<OverdraftPaybackLineCreateData> getLines(final OverdraftedAccount overdraftedAccount,
                                                          final OverdraftPaybackTemplate template) {
        BigDecimal paybackAmount = template.getPaybackAmount();
        BigDecimal ownedAmount = overdraftedAccount.getConvertedAmount();
        Account usingAccount = template.getAccount();

        boolean isOverPaid = paybackAmount.compareTo(ownedAmount) > 0;
        BigDecimal appliableAmount = isOverPaid
                ? ownedAmount
                : paybackAmount;

        List<OverdraftPaybackLineCreateData> lineCreateDatas = isOverPaid
                ? handleOverpaidLines(usingAccount, appliableAmount, paybackAmount)
                : handleNonOverpaidLines(usingAccount, appliableAmount);


        return lineCreateDatas;
    }

    @Override
    @ConsumeOverdraftPaymentSession
    public OverdraftPaybackResponseBody requestOverdraftPayback(
            @OverdraftPaybacKTransactionData final OverdraftPaybackTemplate template) {
        OverdraftedAccount overdraftedAccount = template.getOverdraftedAccount();

        //CONSIDERING TURN THIS INTO PRESET INSTEAD, IF NEEDED IN THE FUTURE
        OverdraftPaybackRequestCreateData requestData =
                OverdraftPaybackRequestDataBuilder.createRequestData(overdraftedAccount);


        return overdraftPaybackInitializer.initializePayback(overdraftedAccount,
                OverdraftPaybackCreateBody.builder()
                        .requestCreateData(requestData)
                        .lineCreateDatas(getLines(overdraftedAccount, template))
                        .build());
    }
}
