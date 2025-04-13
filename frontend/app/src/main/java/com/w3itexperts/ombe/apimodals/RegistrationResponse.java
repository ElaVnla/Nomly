package com.w3itexperts.ombe.apimodals;

public class RegistrationResponse {
    private String message;

    // constructor here =============================================

    public RegistrationResponse() { }

    public RegistrationResponse(String message) {
        this.message = message;
    }

    // getter and setter here ========================================

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
