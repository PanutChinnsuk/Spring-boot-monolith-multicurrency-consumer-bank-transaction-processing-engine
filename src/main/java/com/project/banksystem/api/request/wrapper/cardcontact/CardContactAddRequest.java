package com.project.banksystem.api.request.wrapper.cardcontact;

import jakarta.validation.constraints.NotBlank;

public class CardContactAddRequest {
    @NotBlank
    private String code;


    public CardContactAddRequest() {

    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
