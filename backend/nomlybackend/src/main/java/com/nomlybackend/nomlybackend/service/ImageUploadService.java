package com.nomlybackend.nomlybackend.service;

import com.nomlybackend.nomlybackend.model.Images;
import com.nomlybackend.nomlybackend.model.Profile;
import com.nomlybackend.nomlybackend.model.Users;
import com.nomlybackend.nomlybackend.repository.ImagesRepository;
import com.nomlybackend.nomlybackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.awt.*;
import java.io.IOException;

@Service
public class ImageUploadService {
    @Autowired
    ImagesRepository imagesRepository;
    @Autowired
    UsersRepository usersRepository;

    public Integer uploadProfilePicture(Profile profile, byte[] imageBytes) throws IOException {
        Images image = new Images();
        image.setProfilePicture(imageBytes);
        imagesRepository.save(image);
        return image.getImageId();
    }
}
