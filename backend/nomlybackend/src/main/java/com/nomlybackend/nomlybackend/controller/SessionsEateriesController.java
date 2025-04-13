package com.nomlybackend.nomlybackend.controller;


import com.nomlybackend.nomlybackend.model.Sessions;
import com.nomlybackend.nomlybackend.model.SessionsEateries;
import com.nomlybackend.nomlybackend.model.SessionsEateriesDTO;
import com.nomlybackend.nomlybackend.service.SessionsEateriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sessions-eateries")
public class SessionsEateriesController {

    @Autowired
    SessionsEateriesService sessionsEateriesService;


    @GetMapping("/get-sessions-eateries-by-eateryId/{id}")
    public List<SessionsEateriesDTO> getSessionsEateriesByEateryId(@PathVariable("id") String id){
        return sessionsEateriesService.getAllSessionEateriesByEateryId(id);
    }



    @PostMapping("/add-eatery-to-session")
    public SessionsEateriesDTO addEateryToSession(@RequestBody Map<String, String> body){
        return sessionsEateriesService.addEateryToSession(body);
    }

}
