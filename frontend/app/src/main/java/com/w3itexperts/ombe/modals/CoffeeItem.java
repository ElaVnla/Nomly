package com.w3itexperts.ombe.modals;

public class CoffeeItem {
    private String title;
    private String price;
    private int imageResId;

    public CoffeeItem(String title, String price, int imageResId) {
        this.title = title;
        this.price = price;
        this.imageResId = imageResId;
    }

    // Getters
    public String getTitle() { return title; }
    public String getPrice() { return price; }
    public int getImageResId() { return imageResId; }
}