package com.nomlybackend.nomlybackend.controller;


import com.nomlybackend.nomlybackend.model.Users;
import com.nomlybackend.nomlybackend.repository.UsersRepository;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/get-all-users")
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    @GetMapping("/get-user/{id}")
    public Users getUser(@PathVariable("id") int id){
        return usersRepository.findById(id).get();
    }



    @DeleteMapping("/delete-user/{id}")
    public boolean deleteRow (@PathVariable("id") int id){
        if(!usersRepository.findById(id).equals(Optional.empty())){
            usersRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @PutMapping("/update-user/{id}")
    public Users updateUsers(@PathVariable("id") int id, @RequestBody Map<String,String> body){
        Users current = usersRepository.findById(id).get();


        current.setUsername(body.get("username"));
        current.setEmail(body.get("email"));
        current.setPassword(body.get("password"));
        current.setPreferences(body.get("preferences"));

        usersRepository.save(current);
        return current;

    }


    @PostMapping("/add-user")
    public Users createUser(@RequestBody Map<String,String> body){

        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");
        String preferences = body.get("preferences");
        LocalDateTime now = LocalDateTime.now();
        Date createdAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Users newUser = new Users(username,email,password,preferences,createdAt);

        return usersRepository.save(newUser);
    }
}
