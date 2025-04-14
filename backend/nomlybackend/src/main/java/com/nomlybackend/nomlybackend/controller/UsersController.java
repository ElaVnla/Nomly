package com.nomlybackend.nomlybackend.controller;


import com.nomlybackend.nomlybackend.model.UsersDTO;
import com.nomlybackend.nomlybackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping("/get-all-users")
    public List<UsersDTO> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/get-user/{id}")
    public UsersDTO getUser(@PathVariable("id") int id){
        return usersService.getUserById(id);
    }



    @DeleteMapping("/delete-user/{id}")
    public boolean deleteUser (@PathVariable("id") int id){

        return usersService.deleteUserById(id);
    }


    @PutMapping("/update-user/{id}")
    public UsersDTO updateUsers(@PathVariable("id") int id, @RequestBody Map<String,String> body) throws IOException {
        return usersService.updateUserById(id,body);

    }

    @PostMapping("/add-user")
    public UsersDTO createUser(@RequestBody Map<String,String> body){

        return usersService.createUser(body);
    }
}
