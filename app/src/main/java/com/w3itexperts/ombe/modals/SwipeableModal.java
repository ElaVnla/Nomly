package com.w3itexperts.ombe.modals;

public class SwipeableModal {
    private int profileImage;
    private String title;
    private String date;

    // Constructor
    public SwipeableModal (String title, String date,int profileImage) {
        this.profileImage = profileImage;
        this.title = title;
        this.date = date;
    }

    // Getter and Setter methods
    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
