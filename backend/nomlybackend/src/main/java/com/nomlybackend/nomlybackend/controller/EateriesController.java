package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.eateries.*;
import com.nomlybackend.nomlybackend.service.EateriesPhotosService;
import com.nomlybackend.nomlybackend.service.EateriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (eateries == null){ return ResponseEntity.noContent().build(); } //204: no content
        return ResponseEntity.ok(eateries); //200: OK with list of eateries
    }

    @GetMapping("/get-eatery/{id}")
    public ResponseEntity<EateriesDTO> getEatery(@PathVariable("id") String id) {
        EateriesDTO eatery = eateriesService.getEateryById(id);
        if (eatery == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
        return ResponseEntity.ok(eatery); // 200 OK with the eatery data
    }

    @GetMapping("/get-images/{eateryId}")
    public ResponseEntity<List<byte[]>> getImages(@PathVariable(name = "eateryId") String eateryId) {
        try {
            List<byte[]> images = eateriesPhotosService.getImages(eateryId);
            if (images.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content if no images are found
            }
            return ResponseEntity.ok(images); // 200 OK with the list of images
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 500 Internal Server Error in case of any error
        }
    }

    //Basic methods
    @GetMapping("/get-all-eateries")
    public List<EateriesDTO> getAllEateries() {
        return eateriesService.getAllEateries();
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
}