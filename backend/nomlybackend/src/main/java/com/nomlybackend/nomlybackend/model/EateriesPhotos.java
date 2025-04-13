package com.nomlybackend.nomlybackend.model;

import com.nomlybackend.nomlybackend.model.eateries.Eateries;
import jakarta.persistence.*;

@Entity
@Table(name = "EateriesPhotos")
public class EateriesPhotos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer photoId;

    @Column(name = "PhotoName")
    private String photoName;

    @ManyToOne
    @JoinColumn(name = "EateryId")
    private Eateries eatery;

    public EateriesPhotos(Eateries eatery, String photoName) {
        this.eatery = eatery;
        this.photoName = photoName.split("/photos/")[1];
    }

    public EateriesPhotos(){}

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public Eateries getEatery() {
        return eatery;
    }

    public void setEateryId(Eateries eatery) {
        this.eatery = eatery;
    }
}
