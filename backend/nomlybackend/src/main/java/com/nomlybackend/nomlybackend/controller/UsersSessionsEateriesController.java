package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.UsersDTO;
import com.nomlybackend.nomlybackend.model.UsersSessionsEateriesDTO;
import com.nomlybackend.nomlybackend.service.UsersSessionsEateriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users-sessions-eateries")
public class UsersSessionsEateriesController {

    @Autowired
    UsersSessionsEateriesService usersSessionsEateriesService;

    @GetMapping("/get-users-votes-by-eateryId/{id}")
    public List<UsersSessionsEateriesDTO> getUsersVotesByEateryId(@PathVariable("id") String id){
        return usersSessionsEateriesService.getUsersVotesByEateryId(id);
    }

    @GetMapping("/get-users-votes-by-sessionId/{id}")
    public List<UsersSessionsEateriesDTO> getUsersVotesByEateryId(@PathVariable("id") int id){
        return usersSessionsEateriesService.getUsersVotesBySessionId(id);
    }


    @PostMapping("/user-vote")
    public UsersSessionsEateriesDTO userVote(@RequestBody Map<String, String> body){
        return usersSessionsEateriesService.userVote(body);
    }

    @GetMapping("/get-finished-users/{sessionId}")
    public List<UsersDTO> getFinishedUsers(@PathVariable Integer sessionId){
        return usersSessionsEateriesService.getFinishedUsersBySessionId(sessionId);
    }
}
