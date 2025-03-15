package com.w3itexperts.ombe.modals;

public class OnBordingModal {
    private int imageResId;
    private String title;
    private String subtitle;

    public OnBordingModal(int imageResId, String title, String subtitle) {
        this.imageResId = imageResId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
