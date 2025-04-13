package com.nomlybackend.nomlybackend.model.emails;

import java.time.LocalDateTime;

public class OTPDetails {
    public String code;
    public LocalDateTime expiry;

    public OTPDetails(String code, LocalDateTime expiry) {
        this.code = code;
        this.expiry = expiry;
    }
}