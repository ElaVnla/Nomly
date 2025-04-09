package com.nomlybackend.nomlybackend.model.eateries;


import jakarta.persistence.*;

@Entity
@Table(name = "Eateries")
public class Eateries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "EateryId") //TODO 1.1: UID only for us? or we can use globalCode
    private Integer eateryId;

    @Column (name = "GlobalCode")
    private String globalCode; //TODO 1.1

    //TODO Add displayName
    @Column(name = "DisplayName")
    private String displayName;

//    @Column(name = "Location") //change to 2, lat and long
//    private String location; //TODO 1.2: Change location to Lat and Lng
    @Column(name = "Latitude")
    private Double latitude;

    @Column(name = "Longitude")
    private Double longitude;

    //TODO 1.3: change from PriceRange(old) to PriceLevel in db, then change type to ENUM
    @Column(name = "PriceLevel") //enum type
    private PriceLevel priceLevel;

    @Column(name = "Cuisine") //to add "types"
    private String cuisine;

    @Column(name = "Rating") //TODO 1.4 add rating to db
    private Double rating;

    @Column(name = "OperationHours") //TODO 2: to be implemented, q complicated
    private String operationHours;

    public Eateries() {
    }

    public Integer getEateryId() {
        return eateryId;
    }

    public String getGlobalCode() { return globalCode; }

    public void setGlobalCode(String globalCode) { this.globalCode = globalCode; }

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

    public String getOperationHours() {
        return operationHours;
    }

    public void setOperationHours(String operationHours) {
        this.operationHours = operationHours;
    }
}
