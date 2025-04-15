package com.nomlybackend.nomlybackend.model.eateries;


import jakarta.persistence.*;

@Entity
@Table(name = "Eateries")
public class Eateries {
    @Id
    @Column (name = "EateryId")
    private String eateryId;

    @Column(name = "DisplayName")
    private String displayName;

    @Column(name = "Latitude")
    private Double latitude;

    @Column(name = "Longitude")
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "PriceLevel") //enum type
    private PriceLevel priceLevel;

    @Column(name = "Cuisine") //to add "types"
    private String cuisine;

    @Column(name = "Rating")
    private Double rating;

    @Column(name = "Location")
    private String location;

    @Column(name = "OperationHours") //TODO 1: to be implemented, q complicated
    private String operationHours;

    public Eateries() {
    }

    public Eateries(String eateryId, String displayName, Double latitude, Double longitude,  PriceLevel priceLevel, String cuisine, Double rating, String location, String operationHours) {
        this.eateryId = eateryId;
        this.displayName = displayName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.priceLevel = priceLevel;
        this.cuisine = cuisine;
        this.rating = rating;
        this.location = location;
        this.operationHours = operationHours;
    }

    public String getEateryId() {
        return eateryId;
    }
    public void setEateryId(String eateryId) { this.eateryId = eateryId; }

    public String getDisplayName() { return displayName; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public PriceLevel getPriceLevel() { return priceLevel; }

    public void setPriceLevel(PriceLevel priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getOperationHours() {
        return operationHours;
    }

    public void setOperationHours(String operationHours) {
        this.operationHours = operationHours;
    }
}
