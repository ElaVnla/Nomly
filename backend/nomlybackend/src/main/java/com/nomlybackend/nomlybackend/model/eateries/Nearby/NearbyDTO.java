package com.nomlybackend.nomlybackend.model.eateries.Nearby;

import com.nomlybackend.nomlybackend.model.DietaryRestrictions;
import com.nomlybackend.nomlybackend.model.eateries.Places.LatLng;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NearbyDTO {
    private String[] includedTypes;
    private String[] excludedTypes;
    private int maxResultCount = 10;
    @Embedded
    private LocationRestriction locationRestriction;

    private NearbyDTO(NearbyBuilder builder, String[] includedTypes, String[] excludedTypes){
        this.includedTypes = includedTypes;
        this.excludedTypes = excludedTypes;
        this.maxResultCount = builder.maxResultCount;
        this.locationRestriction = builder.locationRestriction;
    }

    public static class NearbyBuilder{
        private Set<IncludedType> includedTypes = new HashSet<>(List.of(IncludedType.RESTAURANT));
        private Set<ExcludedType> excludedTypes = new HashSet<>(List.of(ExcludedType.GAS_STATION));
        private int maxResultCount = 10;
        private LocationRestriction locationRestriction;

        public NearbyBuilder(){}

        public NearbyBuilder setLatLong(double latitude, double longitude){
            this.locationRestriction = new LocationRestriction(
                    new Circle(new LatLng(latitude, longitude), 500));
            return this;
        }

        public NearbyBuilder addDietaryRestriction(DietaryRestrictions restrictions) {
            this.includedTypes.addAll(Arrays.asList(restrictions.getInclusions()));
            this.excludedTypes.addAll(Arrays.asList(restrictions.getExclusions()));
            return this;
        }

        public NearbyBuilder setMaxResultCount(int results){
            this.maxResultCount = results;
            return this;
        }

        public NearbyDTO build() {
            // Convert Sets to arrays of Strings
            String[] includedTypesArray = includedTypes.stream()
                    .map(IncludedType::getType)  // Convert each IncludedType to its corresponding String
                    .toArray(String[]::new);
            String[] excludedTypesArray = excludedTypes.stream()
                    .map(ExcludedType::getType)  // Convert each ExcludedType to its corresponding String
                    .toArray(String[]::new);

            // Create and return the Nearby object with the set data
            return new NearbyDTO(this, includedTypesArray, excludedTypesArray);
        }
    }

    public void increaseRange(){
        this.locationRestriction.increaseCircle();
    }

    public Double getRange(){ return this.locationRestriction.getCircle().getRadius(); }

    public String[] getIncludedTypes() {
        return includedTypes;
    }

    public void setIncludedTypes(String[] includedTypes) {
        this.includedTypes = includedTypes;
    }

    public String[] getExcludedTypes() {
        return excludedTypes;
    }

    public int getMaxResultCount() {
        return maxResultCount;
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

