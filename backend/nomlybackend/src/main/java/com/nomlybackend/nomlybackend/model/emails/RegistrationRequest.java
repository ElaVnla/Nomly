package com.nomlybackend.nomlybackend.model.emails;

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
