package com.project.banksystem.application.backend.refreshtoken.body;

import java.util.Map;

public class RefreshResponseBody {
    private Map<String, String> values;

    public RefreshResponseBody(Map<String, String> values) {
        this.values = values;
    }

    public Map<String, String> getValues() {
        return values;
    }
}
