package com.nomlybackend.nomlybackend.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Profile extends CreateDatabaseEntry{
    @OneToOne
    @JoinColumn (name = "ImageId",  referencedColumnName = "ImageId")
    private Images profilePicture;

    public Images getImage() { return this.profilePicture; }

    public void setImage(Images image) { this.profilePicture = image; }
}
