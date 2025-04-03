package com.w3itexperts.ombe.modals;

import java.util.List;

//public class RestaurantCard {
//    private String name;
//    private String location;
//    private String priceLevel;
//    private int imageResId; // Just one image, the full collage
//
//    public RestaurantCard(String name, String location, String priceLevel, int imageResId) {
//        this.name = name;
//        this.location = location;
//        this.priceLevel = priceLevel;
//        this.imageResId = imageResId;
//    }
//
//    public String getName() { return name; }
//    public String getLocation() { return location; }
//    public String getPriceLevel() { return priceLevel; }
//    public int getImageResId() { return imageResId; }
//}

public class RestaurantCard {
    private String name;
    private String location;
    private String priceLevel;
    private int imageResId;

    public RestaurantCard(String name, String location, String priceLevel, int imageResId) {
        this.name = name;
        this.location = location;
        this.priceLevel = priceLevel;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public int getImageResId() {
        return imageResId;
    }
}