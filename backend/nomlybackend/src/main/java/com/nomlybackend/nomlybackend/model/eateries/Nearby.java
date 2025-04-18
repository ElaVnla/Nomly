package com.nomlybackend.nomlybackend.model.eateries;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

public class Nearby {
    private String[] includedTypes = {"restaurant"};
    private String[] excludedTypes = {"gas_station"};
    private int maxResultCount = 10;

    @Embedded
    private LocationRestriction locationRestriction;

    public void setLatLong(double latitude, double longitude){
        this.locationRestriction = new LocationRestriction(
                new Circle(new LatLng(latitude, longitude), 500));
    }

    public void increaseRange(){
        this.locationRestriction.increaseCircle();
    }

    public Double getRange(){ return this.locationRestriction.getCircle().getRadius(); }

    public Nearby(double lat, double lng){
        this.setLatLong(lat, lng);
    }

    public String[] getIncludedTypes() {
        return includedTypes;
    }

    public void setIncludedTypes(String[] includedTypes) {
        this.includedTypes = includedTypes;
    }

    public int getMaxResultCount() {
        return maxResultCount;
    }

    public void setMaxResultCount(int maxResultCount) {
        this.maxResultCount = maxResultCount;
    }

    LocationRestriction getLocationRestriction() {
        return locationRestriction;
    }

    void setLocationRestriction(LocationRestriction locationRestriction) {
        this.locationRestriction = locationRestriction;
    }
}

@Embeddable
class LocationRestriction{
    @Embedded
    private Circle circle;
    LocationRestriction(Circle circle){
        this.circle = circle;
    }
    LocationRestriction(){}

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void increaseCircle() {this.circle.setRadius(circle.getRadius()*2);}
}

@Embeddable
class Circle{
    private LatLng center;
    private double radius;
    Circle(LatLng center, double radius){
        this.center = center;
        this.radius = radius;
    }
    Circle(){}

    public LatLng getCenter() {
        return center;
    }

    public void setCenter(LatLng center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
