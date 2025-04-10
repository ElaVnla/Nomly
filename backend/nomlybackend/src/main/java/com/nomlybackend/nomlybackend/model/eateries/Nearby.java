package com.nomlybackend.nomlybackend.model.eateries;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

public class Nearby {
    private String[] includedTypes = {"restaurant"};
    private int maxResultCount = 10;

    @Embedded
    private LocationRestriction locationRestriction;

    public void setLatLong(double latitude, double longitude){
        this.locationRestriction = new LocationRestriction(
                new Circle(new LatLng(latitude, longitude), 500.0));
    }

    public Nearby(double lat, double lng){
        this.setLatLong(lat, lng);
    }
}

@Embeddable
class LocationRestriction{
    @Embedded
    private Circle circle;
    LocationRestriction(Circle circle){
        this.circle = circle;
    }
}

@Embeddable
class Circle{
    private LatLng center;
    private double radius;
    Circle(LatLng center, double radius){
        this.center = center;
        this.radius = radius;
    }
}
