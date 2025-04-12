package com.nomlybackend.nomlybackend.controller;


import com.nomlybackend.nomlybackend.model.UsersDTO;
import com.nomlybackend.nomlybackend.model.UsersGroupings;
import com.nomlybackend.nomlybackend.repository.UsersGroupingsRepository;
import com.nomlybackend.nomlybackend.service.UsersGroupingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users-groupings")
public class UsersGroupingsController {

    @Autowired
    UsersGroupingsService usersGroupingsService;

    @PostMapping("/add-user-to-grouping")
    public UsersGroupings addUserToGrouping(@RequestBody Map<String,String> body){
        return usersGroupingsService.addUserToGrouping(body);
    }


    // @DeleteMapping("/remove-user-from-grouping")
    //public boolean removeUserFromGrouping(@RequestBody Map<String,String> body){
        //return usersGroupingsService.removeUserFromGrouping(body);
    //}
    @PostMapping("/remove-user-from-grouping")
    public boolean removeUserFromGrouping(@RequestBody Map<String,String> body){
        return usersGroupingsService.removeUserFromGrouping(body);
    }



}
