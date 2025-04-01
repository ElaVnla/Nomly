package com.nomlybackend.nomlybackend.model.restaurant;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Restaurant {
    @Id
    @Embedded
    private PlusCode plusCode; //plusCode

    @Embedded
    private LatLng location; //location

    @Embedded
    private OpeningHours regularOpeningHours;
    private double startPrice; //priceRange
    private double endPrice; //priceRange
    private double cuisine;

    public Restaurant(PlusCode plusCode, LatLng latLng, OpeningHours regularOpeningHours, double startPrice, double endPrice, double cuisine) {
        this.plusCode = plusCode;
        this.location = latLng;
        this.regularOpeningHours = regularOpeningHours;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.cuisine = cuisine;
    }

    public Restaurant(){

    }
}

@Embeddable
class PlusCode{
    private String globalCode;
    private String compoundCode;
}