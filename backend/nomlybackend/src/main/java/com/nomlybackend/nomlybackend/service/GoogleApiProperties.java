package com.nomlybackend.nomlybackend.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.api")
public class GoogleApiProperties {
    private String key;

    public String key() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
