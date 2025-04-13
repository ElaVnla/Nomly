package com.w3itexperts.ombe.apimodals;

public class RegistrationRequest {
    private String email;

    // Constructor here =======================

    public RegistrationRequest() { }

    public RegistrationRequest(String email) {
        this.email = email;
    }

    // Getter and setter here ===========================

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
