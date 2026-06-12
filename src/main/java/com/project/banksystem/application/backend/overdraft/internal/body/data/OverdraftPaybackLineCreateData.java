package com.project.banksystem.application.backend.overdraft.internal.body.data;

import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackLineDirection;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRole;
import com.project.banksystem.infrastructure.model.Account;

import java.math.BigDecimal;

public class OverdraftPaybackLineCreateData {
    private Account account;
    private BigDecimal paybackAmount;
    private OverdraftPaybackLineDirection overdraftPaybackLineDirection;
    private OverdraftPaybackRole overdraftPaybackRole;


    public OverdraftPaybackLineCreateData(Account account,
                                          BigDecimal paybackAmount,
                                          OverdraftPaybackLineDirection overdraftPaybackLineDirection,
                                          OverdraftPaybackRole overdraftPaybackRole) {
        this.account = account;
        this.paybackAmount = paybackAmount;
        this.overdraftPaybackLineDirection = overdraftPaybackLineDirection;
        this.overdraftPaybackRole = overdraftPaybackRole;
    }

    public static Builder builder() {
        return new Builder();
    }



    public static class Builder {
        Account account;
        BigDecimal paybackAmount;
        OverdraftPaybackLineDirection overdraftPaybackLineDirection;
        OverdraftPaybackRole overdraftPaybackRole;

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder paybackAmount(BigDecimal paybackAmount) {
            this.paybackAmount = paybackAmount;
            return this;

        }

        public Builder overdraftPaybackLineDirection(OverdraftPaybackLineDirection overdraftPaybackLineDirection) {
            this.overdraftPaybackLineDirection = overdraftPaybackLineDirection;
            return this;
        }

        public Builder overdraftPaybackRole(OverdraftPaybackRole overdraftPaybackRole) {
            this.overdraftPaybackRole = overdraftPaybackRole;
            return this;
        }

        public OverdraftPaybackLineCreateData build() {
            if (account == null) {
                throw new IllegalStateException("Account is null.");
            }

            if (overdraftPaybackLineDirection == null) {
                throw new IllegalStateException("Overdraft payback line direction is null.");
            }

            if (paybackAmount == null) {
                throw new IllegalStateException("Paid back amount is null.");
            }

            if (overdraftPaybackRole == null) {
                throw new IllegalStateException("Overdraft payback role is null.");
            }

            return new OverdraftPaybackLineCreateData(account, paybackAmount,
                    overdraftPaybackLineDirection, overdraftPaybackRole);
        }
    }




    public Account getAccount() {
        return account;
    }

    public OverdraftPaybackRole getOverdraftPaybackRole() {
        return overdraftPaybackRole;
    }

    public BigDecimal getPaybackAmount() {
        return paybackAmount;
    }

    public OverdraftPaybackLineDirection getOverdraftPaybackLineDirection() {
        return overdraftPaybackLineDirection;
    }
}
