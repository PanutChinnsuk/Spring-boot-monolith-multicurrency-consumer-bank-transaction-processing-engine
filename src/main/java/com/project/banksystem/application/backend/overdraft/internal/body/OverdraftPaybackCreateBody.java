package com.project.banksystem.application.backend.overdraft.internal.body;

import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackLineCreateData;
import com.project.banksystem.application.backend.overdraft.internal.body.data.OverdraftPaybackRequestCreateData;

import java.util.List;

public class OverdraftPaybackCreateBody {
    private OverdraftPaybackRequestCreateData requestCreateData;
    private List<OverdraftPaybackLineCreateData> lineCreateDatas;

    public OverdraftPaybackCreateBody(OverdraftPaybackRequestCreateData requestCreateData,
                                      List<OverdraftPaybackLineCreateData> lineCreateDatas) {
        this.requestCreateData = requestCreateData;
        this.lineCreateDatas = lineCreateDatas;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        OverdraftPaybackRequestCreateData requestCreateData;
        List<OverdraftPaybackLineCreateData> lineCreateDatas;

        public Builder requestCreateData(OverdraftPaybackRequestCreateData requestCreateData) {
            this.requestCreateData = requestCreateData;
            return this;
        }

        public Builder lineCreateDatas(List<OverdraftPaybackLineCreateData> lineCreateDatas) {
            this.lineCreateDatas = lineCreateDatas;
            return this;
        }

        public OverdraftPaybackCreateBody build() {
            if (requestCreateData == null) {
                throw new IllegalStateException("Request create data is null.");
            }

            if (lineCreateDatas == null || lineCreateDatas.isEmpty()) {
                throw new IllegalStateException("Line create datas is null or empty.");
            }

            return new OverdraftPaybackCreateBody(requestCreateData, lineCreateDatas);
        }
    }

    public List<OverdraftPaybackLineCreateData> getLineCreateDatas() {
        return lineCreateDatas;
    }

    public OverdraftPaybackRequestCreateData getRequestCreateData() {
        return requestCreateData;
    }
}
