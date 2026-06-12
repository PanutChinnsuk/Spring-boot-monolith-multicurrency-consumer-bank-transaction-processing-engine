package com.project.banksystem.api.request.dto.banktransaction.admin.data;

import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.AccountNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CustomTransactionLineCreateDTO {
    @AccountNumber
    private String accountNumber;

    @NotBlank
    private String ledgerCode;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private TransactionLineDirection transactionLineDirection;

    @NotNull
    private BalanceImpact balanceImpact;

    public CustomTransactionLineCreateDTO() {

    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionLineDirection(TransactionLineDirection transactionLineDirection) {
        this.transactionLineDirection = transactionLineDirection;
    }

    public void setLedgerCode(String ledgerCode) {
        this.ledgerCode = ledgerCode;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalanceImpact(BalanceImpact balanceImpact) {
        this.balanceImpact = balanceImpact;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionLineDirection getTransactionLineDirection() {
        return transactionLineDirection;
    }

    public BalanceImpact getBalanceImpact() {
        return balanceImpact;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getLedgerCode() {
        return ledgerCode;
    }
}
