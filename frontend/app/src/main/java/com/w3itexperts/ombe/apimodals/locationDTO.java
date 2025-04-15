package com.w3itexperts.ombe.apimodals;

public class locationDTO {
    private String latitude;
    private String longitude;
    private int sessionId;

    public locationDTO(double latitude, double longitude, int sessionId) {
        this.latitude = String.valueOf(latitude);
        this.longitude = String.valueOf(longitude);
        this.sessionId = sessionId;
    }

    // Getters
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getSessionId() {
        return sessionId;
    }

    // Setters (needed for Retrofit's Gson to work properly)
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}