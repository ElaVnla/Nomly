package com.w3itexperts.ombe.apimodals;

public class RegistrationRequest {
    private String email;

    public RegistrationRequest() { }

    public RegistrationRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
