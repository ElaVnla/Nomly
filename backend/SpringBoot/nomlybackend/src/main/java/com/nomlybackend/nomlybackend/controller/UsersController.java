package com.nomlybackend.nomlybackend.controller;


import com.nomlybackend.nomlybackend.model.Users;
import com.nomlybackend.nomlybackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/get-all-users")
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

}
