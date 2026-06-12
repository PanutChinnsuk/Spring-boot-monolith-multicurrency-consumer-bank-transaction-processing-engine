package com.project.banksystem.application.backend;

import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.core.response.Result;

public final class DataResultBuilder {
    private DataResultBuilder() {

    }

    public static final Result build(final String message) {
        return new Result(message);
    }

    public static final <T> DataResult<T> build(final T body,
                                         final String message) {
        return new DataResult<T>(body, message);
    }

}
