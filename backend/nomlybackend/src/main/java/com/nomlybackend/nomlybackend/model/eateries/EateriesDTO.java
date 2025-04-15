package com.nomlybackend.nomlybackend.model.eateries;

public class EateriesDTO {


    private String eateryId;
    private String displayName;
    private Double latitude;
    private Double longitude;
    private PriceLevel priceLevel;
    private String cuisine;
    private Double rating;
    private String location;
    private String operationHours;


    public EateriesDTO(Eateries eatery) {
        this.eateryId = eatery.getEateryId();
        this.displayName = eatery.getDisplayName();
        this.latitude = eatery.getLatitude();
        this.longitude = eatery.getLongitude();
        this.priceLevel = eatery.getPriceLevel();
        this.cuisine = eatery.getCuisine();
        this.rating = eatery.getRating();
        this.location = eatery.getLocation();
        this.operationHours = eatery.getOperationHours();
    }

    public String getEateryId() {
        return eateryId;
    }

    public void setEateryId(String eateryId) {
        this.eateryId = eateryId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public PriceLevel getPriceLevel() {
        return priceLevel;
    }

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
