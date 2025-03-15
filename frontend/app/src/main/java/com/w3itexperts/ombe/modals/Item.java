package com.w3itexperts.ombe.modals;
public class Item {
    private int imageResId;
    private String title;
    private String date;

    public Item(int imageResId, String title, String date) {
        this.imageResId = imageResId;
        this.title = title;
        this.date = date;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
