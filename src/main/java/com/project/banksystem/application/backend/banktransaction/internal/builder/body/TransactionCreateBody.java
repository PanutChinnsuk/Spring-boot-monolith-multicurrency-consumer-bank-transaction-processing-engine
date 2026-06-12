package com.project.banksystem.application.backend.banktransaction.internal.builder.body;

import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionCreateData;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;


import java.util.List;


public class TransactionCreateBody {
    private TransactionCreateData transactionCreateData;

    private List<TransactionLineCreateData> transactionLineCreateDatas;



    public TransactionCreateBody(TransactionCreateData transactionCreateData,
                                 List<TransactionLineCreateData> transactionLineCreateDatas) {
        this.transactionCreateData = transactionCreateData;
        this.transactionLineCreateDatas = transactionLineCreateDatas;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        TransactionCreateData transactionCreateData;
        List<TransactionLineCreateData> transactionLineCreateDatas;

        public Builder transactionCreateData(TransactionCreateData transactionCreateData) {
            this.transactionCreateData = transactionCreateData;
            return this;
        }

        public Builder transactionLineCreateDatas(List<TransactionLineCreateData> transactionLineCreateDatas) {
            this.transactionLineCreateDatas = transactionLineCreateDatas;
            return this;
        }

        public TransactionCreateBody build() {
            if (transactionCreateData == null) {
                throw new IllegalStateException("Transaction create data is null.");
            }
            if (transactionLineCreateDatas == null || transactionLineCreateDatas.isEmpty()) {
                throw new IllegalStateException("Transaction line create datas is null or empty.");
            }

            return new TransactionCreateBody(transactionCreateData, transactionLineCreateDatas);
        }
    }



    public List<TransactionLineCreateData> getTransactionLineCreateDatas() {
        return transactionLineCreateDatas;
    }

    public TransactionCreateData getTransactionCreateData() {
        return transactionCreateData;
    }


}
