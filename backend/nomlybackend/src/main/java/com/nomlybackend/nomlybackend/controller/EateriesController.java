package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.eateries.*;
import com.nomlybackend.nomlybackend.repository.EateriesRepository;
import com.nomlybackend.nomlybackend.service.EateriesPhotosService;
import com.nomlybackend.nomlybackend.service.EateriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController //returns json
@RequestMapping("/eateries")
public class EateriesController {
    @Autowired
    EateriesService eateriesService;
    @Autowired
    EateriesPhotosService eateriesPhotosService;

    @PutMapping("/find-eateries")
    public ResponseEntity<List<Eateries>> findEateries(@RequestBody LocationDTO locationDTO) throws Exception {
        List<Eateries> eateries = eateriesService.findEateries(locationDTO);
        if (eateries == null){ return ResponseEntity.noContent().build(); }
        return ResponseEntity.ok(eateries);
    }

    @GetMapping("/get-all-eateries")
    public List<EateriesDTO> getAllEateries() {
        return eateriesService.getAllEateries();
    }

    @GetMapping("/get-eatery/{id}")
    public EateriesDTO getEatery(@PathVariable("id") String id) {
        return eateriesService.getEateryById(id);
    }

    @DeleteMapping("/delete-eatery/{id}")
    public boolean deleteRow(@PathVariable("id") String id) {
        return eateriesService.deleteEaterybyId(id);
    }

    @PutMapping("/update-eatery/{id}")
    public EateriesDTO updateEatery(@PathVariable("id") String id, @RequestBody Map<String, String> body) {
        return eateriesService.updateEateryById(id, body);
    }

    @PostMapping("/add-eatery")
    public EateriesDTO createEatery(@RequestBody Map<String, String> body) {
        return eateriesService.createEatery(body);
    }

    @GetMapping("/get-images/{eateryId}")
    public List<byte[]> getImages(@PathVariable(name = "eateryId") String eateryId) throws Exception {
        return eateriesPhotosService.getImages(eateryId);
    }
}