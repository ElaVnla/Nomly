package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.EateriesPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EateriesPhotosRepository extends JpaRepository<EateriesPhotos, Integer> {
    @Query("SELECT p.photoName FROM EateriesPhotos p WHERE p.eatery.eateryId = :eateryId")
    List<String> findPhotoNamesByEateryId(@Param("eateryId") String eateryId);
}
