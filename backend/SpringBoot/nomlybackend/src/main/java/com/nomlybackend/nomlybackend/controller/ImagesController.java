package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.Images;
import com.nomlybackend.nomlybackend.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Map;
import java.util.Optional;


@RestController
public class ImagesController {
    @Autowired
    ImagesRepository imagesRepository;

    @GetMapping("/get-image/{id}")
    public Images getImage(@PathVariable("id") int id){
        return imagesRepository.findById(id).get();
    }

    @DeleteMapping("/remove-image/{id}")
    public boolean deleteRow (@PathVariable("id") int id){
        if(!imagesRepository.findById(id).equals(Optional.empty())){
            imagesRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @PostMapping(value = "/add-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public int createImage(@RequestParam("image")MultipartFile image){
        try{
            Images newImage = new Images(image);
            newImage = imagesRepository.save(newImage);
            return newImage.getImageId();
        }catch (Exception ex){
            return 0;
        }
    }


//    @PostMapping("/add")
//    public Images createAddress(@RequestBody Map<String,String> body){
//        String street = body.get("street");
//        String postcode = body.get("postcode");
//        Integer number = Integer.parseInt(body.get("number"));
//        Address newAddress = new Address(number,street,postcode);
//
//        return mySqlRepository.save(newAddress);
//    }
}
