package com.nomlybackend.nomlybackend.model;

import com.nomlybackend.nomlybackend.service.ImageDownloadService;
import jakarta.persistence.*;

@Entity
@Table(name = "Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ImageId")
    private Integer imageId;

    @Lob
    @Column(name = "Image")
    private byte[] Image;

    public Images(byte[] image, Users user){
        this.Image = image;
    }

    public Images() {}

    public Integer getImageId() { return imageId;}

    public byte[] getProfilePicture() { return Image; }

    public void setProfilePicture(byte[] profilePicture) { this.Image = profilePicture;}
}
