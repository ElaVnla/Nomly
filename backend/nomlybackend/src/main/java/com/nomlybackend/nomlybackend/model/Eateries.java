package com.nomlybackend.nomlybackend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Eateries")
public class Eateries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "EateryId")
    private Integer eateryId;

    @Column(name = "Location")
    private String location;

    @Column(name = "OperationHours")
    private String operationHours;

    @Column(name = "PriceRange")
    private String priceRange;

    @Column(name = "Cuisine")
    private String cuisine;

    @Column(name = "Rating")
    private Double rating;

    public Eateries() {
    }

    public Eateries(String location, String operationHours, String priceRange, String cuisine, Double rating) {
        this.location = location;
        this.operationHours = operationHours;
        this.priceRange = priceRange;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public Integer getEateryId() {
        return eateryId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOperationHours() {
        return operationHours;
    }

    public void setOperationHours(String operationHours) {
        this.operationHours = operationHours;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
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
}
