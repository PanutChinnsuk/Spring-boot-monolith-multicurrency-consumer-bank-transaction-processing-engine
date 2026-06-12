package com.project.banksystem.api.request.dto.credential;

import com.project.banksystem.infrastructure.model.User;

public class CredentialInternalCreateDTO {
    private User user;

    private String password;

    private String pin;


    public CredentialInternalCreateDTO(User user,
                                       String password,
                                       String pin) {
        this.user = user;
        this.password = password;
        this.pin = pin;

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        User user;
        String password;
        String pin;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder pin(String pin) {
            this.pin = pin;
            return this;
        }

        public CredentialInternalCreateDTO build() {
            return new CredentialInternalCreateDTO(user, password, pin);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public String getPin() {
        return pin;
    }

    public String getPassword() {
        return password;
    }
}

