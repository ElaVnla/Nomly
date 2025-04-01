package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.restaurant.LatLng;
import com.nomlybackend.nomlybackend.model.restaurant.Places;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //returns json
public class RestaurantController {
    @GetMapping("/")
    public String getRestaurant() throws Exception {
        return Places.getRestaurants(new LatLng(1.334840, 103.964714));
    }
}
