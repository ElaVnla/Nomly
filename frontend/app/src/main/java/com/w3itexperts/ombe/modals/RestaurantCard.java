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
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RestaurantCard implements Parcelable {
    private String name;
    //private String location;
    private String cuisine;
    private String priceLevel;
    private Bitmap image;
    private String eateryId;

    // private List<Bitmap> images;

//    public RestaurantCard(String name, String location, String priceLevel, int imageResId) {
//        this.name = name;
//        this.location = location;
//        this.priceLevel = priceLevel;
//        this.imageResId = imageResId;
//    }

    public RestaurantCard(String name, String cuisine, String priceLevel, Bitmap image, String eateryId) {
        this.name = name;
        // this.location = location;
        this.cuisine = cuisine;
        this.priceLevel = priceLevel;
        this.image = image;
        this.eateryId = eateryId;
    }

    public String getName() {
        return name;
    }

//    public String getLocation() {
//        return location;
//    }

    public String getPriceLevel() {
        return priceLevel;
    }
    //
    public Bitmap getImage() {
        return image;
    }

//    public int getImageResId() {
//        return imageResId;
//    }

//    public List<Bitmap> getImages() {
//        return images;
//    }

    public String getCuisine() {
        return cuisine;
    }

    public String getEateryId() {
        return eateryId;
    }

    protected RestaurantCard(Parcel in) {
        eateryId = in.readString();
        name = in.readString();
        priceLevel = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }


    public static final Creator<RestaurantCard> CREATOR = new Creator<RestaurantCard>() {
        @Override
        public RestaurantCard createFromParcel(Parcel in) {
            return new RestaurantCard(in);
        }

        @Override
        public RestaurantCard[] newArray(int size) {
            return new RestaurantCard[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eateryId);
        dest.writeString(name);
        dest.writeString(priceLevel);
        dest.writeParcelable(image, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }





}