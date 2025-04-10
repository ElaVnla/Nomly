package com.nomlybackend.nomlybackend.model.eateries;

public class LocationDTO {
    private Double latitude;
    private Double longitude;
    private String sessionId;

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getSessionId() {
        return sessionId;
    }
}
