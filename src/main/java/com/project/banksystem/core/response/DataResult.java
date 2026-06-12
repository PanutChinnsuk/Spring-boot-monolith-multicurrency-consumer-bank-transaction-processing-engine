package com.project.banksystem.core.response;

public class DataResult<T> extends Result {
    private T body;

    public DataResult(T body, String message) {
        super(message);
        this.body = body;

    }



    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public T getBody() {
        return body;
    }


}
