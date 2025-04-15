package com.nomlybackend.nomlybackend.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Profile {
    @Column (name = "CreatedAt")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn (name = "ImageId",  referencedColumnName = "ImageId")
    private Images profilePicture;

    Profile(){ setCreatedAt(); }

    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public Images getImage() { return this.profilePicture; }

    public void setImage(Images image) { this.profilePicture = image; }
}
