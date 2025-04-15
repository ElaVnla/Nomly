package com.w3itexperts.ombe.modals;
import com.w3itexperts.ombe.apimodals.eateries;

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

import android.graphics.Bitmap;

public class RestaurantCard {
    private String name;
    private String location;
    private String priceLevel;
    private Bitmap image;
    private String eateryId;

//    public RestaurantCard(String name, String location, String priceLevel, int imageResId) {
//        this.name = name;
//        this.location = location;
//        this.priceLevel = priceLevel;
//        this.imageResId = imageResId;
//    }

    public RestaurantCard(String name, String location, String priceLevel, Bitmap image, String eateryId) {
        this.name = name;
        this.location = location;
        this.priceLevel = priceLevel;
        this.image = image;
        this.eateryId = eateryId;
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

//    public int getImageResId() {
//        return imageResId;
//    }

    public Bitmap getImage() {
        return image;
    }
    public String getEateryId() {
        return eateryId;
    }
}