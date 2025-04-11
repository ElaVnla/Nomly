package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.eateries.*;
import com.nomlybackend.nomlybackend.repository.EateryRepository;
import com.nomlybackend.nomlybackend.service.EateriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //returns json
@RequestMapping("/eateries")
public class EateriesController {

    @Autowired
    EateryRepository eateryRepository;

    @PutMapping("/find-eateries")
    public List<Eateries> findEateries(@RequestBody LocationDTO locationDTO) throws Exception {
        return EateriesService.findEateries(locationDTO);
    }

    @GetMapping("/get-eateries/{sessionId}")
    public String getRestaurant() throws Exception {
        return "temp";
    }
}
