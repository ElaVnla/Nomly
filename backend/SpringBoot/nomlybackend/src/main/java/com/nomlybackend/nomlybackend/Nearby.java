package com.nomlybackend.nomlybackend;

import java.util.List;

public class Nearby {
    private String[] includedTypes = {"restaurant"};
    private int maxResultCount = 10;
    private LocationRestriction locationRestriction;

    public void setLatLong(double latitude, double longitude){
        this.locationRestriction = new LocationRestriction(
                new Circle(
                        new LatLong(latitude, longitude), 500.0));
    }
}

class LocationRestriction{
    private Circle circle;
    LocationRestriction(Circle circle){
        this.circle = circle;
    }
}

class Circle{
    private LatLong center;
    private double radius;
    Circle(LatLong center, double radius){
        this.center = center;
        this.radius = radius;
    }
}
