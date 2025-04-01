package com.nomlybackend.nomlybackend.model.restaurant;

import jakarta.persistence.Embeddable;

@Embeddable
public class LatLng{
    private double latitude;
    private double longitude;

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
