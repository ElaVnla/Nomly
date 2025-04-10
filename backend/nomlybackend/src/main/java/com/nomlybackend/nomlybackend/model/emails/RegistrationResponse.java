package com.nomlybackend.nomlybackend.model.emails;

public class RegistrationResponse {
    private String message;

    public RegistrationResponse() { }

    public RegistrationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
