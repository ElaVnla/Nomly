package com.nomlybackend.nomlybackend.model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "Images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ImageId")
    private Integer imageId;
    @Column (name = "Image")
    private MultipartFile image;

    Images(){}

    public Images(MultipartFile image){
        this.image = image;
    }

    public Integer getImageId() {
        return imageId;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
