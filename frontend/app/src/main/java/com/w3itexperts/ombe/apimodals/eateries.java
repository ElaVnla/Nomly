package com.w3itexperts.ombe.apimodals;
public class eateries {
    private String eateryId;
    private String displayName;
    private Double latitude;
    private Double longitude;
    private String priceLevel;  // enum will come as a String from backend
    private String cuisine;
    private Double rating;
    private String operationHours;

    // Required empty constructor
    public eateries() {}

    // Getters
    public String getEateryId() { return eateryId; }
    public String getDisplayName() { return displayName; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getPriceLevel() { return priceLevel; }
    public String getCuisine() { return cuisine; }
    public Double getRating() { return rating; }
    public String getOperationHours() { return operationHours; }

    // Setters
    public void setEateryId(String eateryId) { this.eateryId = eateryId; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public void setPriceLevel(String priceLevel) { this.priceLevel = priceLevel; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setOperationHours(String operationHours) { this.operationHours = operationHours; }
}
