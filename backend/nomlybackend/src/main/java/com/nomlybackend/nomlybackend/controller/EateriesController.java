package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.eateries.*;
import com.nomlybackend.nomlybackend.repository.EateryRepository;
import com.nomlybackend.nomlybackend.service.EateriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController //returns json
@RequestMapping("/eateries")
public class EateriesController {

//    @Autowired
//    EateryRepository eateryRepository;

    @Autowired
    EateriesService eateriesService;

    @PutMapping("/find-eateries")
    public List<Eateries> findEateries(@RequestBody LocationDTO locationDTO) throws Exception {
        return EateriesService.findEateries(locationDTO);
    }

    @GetMapping("/get-all-eateries")
    public List<EateriesDTO> getAllEateries(){
        return eateriesService.getAllEateries();
    }

//    @GetMapping("/get-eateries/{sessionId}")
//    public String getRestaurant() throws Exception {
//        return "temp";
//    }

    @GetMapping("/get-eatery/{id}")
    public EateriesDTO getEatery(@PathVariable("id") String id){
        return eateriesService.getEateryById(id);
    }

    @DeleteMapping("/delete-eatery/{id}")
    public boolean deleteRow(@PathVariable("id") String id){
        return eateriesService.deleteEaterybyId(id);
    }

    @PutMapping("/update-eatery/{id}")
    public EateriesDTO updateEatery(@PathVariable("id") String id, @RequestBody Map<String,String> body){
        return eateriesService.updateEateryById(id, body);
    }

    @PostMapping("/add-eatery")
    public EateriesDTO createEatery(@RequestBody Map<String,String> body){
        return eateriesService.createEatery(body);
    }


}
