package com.nomlybackend.nomlybackend.model.eateries;

import com.google.gson.annotations.SerializedName;

public class PhotoDTO {

    @SerializedName("photoUri")
    private String photoUri;

    // Getter and Setter for photoUri
    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
