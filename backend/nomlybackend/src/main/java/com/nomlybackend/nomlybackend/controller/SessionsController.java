package com.nomlybackend.nomlybackend.controller;


import com.nomlybackend.nomlybackend.model.SessionsDTO;
import com.nomlybackend.nomlybackend.service.SessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sessions")
public class SessionsController {
    @Autowired
    SessionsService sessionsService;

    @GetMapping("/get-all-sessions")
    public List<SessionsDTO> getAllSessions(){
        return sessionsService.getAllSessions();
    }

    @GetMapping("/get-session/{id}")
    public SessionsDTO getSession(@PathVariable("id") int id){
        return sessionsService.getSessionById(id);
    }

    @DeleteMapping("/delete-session/{id}")
    public boolean deleteSession(@PathVariable("id") int id){
        return sessionsService.deleteSessionById(id);
    }

    @PutMapping("/update-session/{id}")
    public SessionsDTO updateSession(@PathVariable("id") int id, @RequestBody Map<String,String> body){
        return sessionsService.updateSessionById(id,body);
    }

    @PostMapping("/add-session")
    public SessionsDTO createSession(@RequestBody Map<String,String>body){
        return sessionsService.createSession(body);
    }

    @PutMapping("/session-completed/{id}")
    public SessionsDTO sessionCompleted(@PathVariable("id") int id){
        return sessionsService.sessionCompleted(id);
    }
}
