package com.w3itexperts.ombe.apimodals;

import android.media.Image;

public class images {
    private int ImageId;
    private Image Image;

    public images(int imageId, android.media.Image image) {
        ImageId = imageId;
        Image = image;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public android.media.Image getImage() {
        return Image;
    }

    public void setImage(android.media.Image image) {
        Image = image;
    }
}
