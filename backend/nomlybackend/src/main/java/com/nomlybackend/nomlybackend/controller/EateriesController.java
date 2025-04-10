package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.eateries.*;
import com.nomlybackend.nomlybackend.repository.EateryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //returns json
public class EateriesController {

    @Autowired
    EateryRepository eateryRepository;

    @PutMapping("/find-eateries")
    public List<Eateries> findEateries(@RequestBody LocationDTO locationDTO) throws Exception {
        return Places.findEateries(locationDTO);
    }

    @GetMapping("/get-eateries/{sessionId}")
    public String getRestaurant() throws Exception {
        return "temp";
    }
}
