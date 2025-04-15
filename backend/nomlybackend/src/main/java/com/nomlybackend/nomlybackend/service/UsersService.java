package com.nomlybackend.nomlybackend.service;

import com.nomlybackend.nomlybackend.model.Images;
import com.nomlybackend.nomlybackend.model.Users;
import com.nomlybackend.nomlybackend.model.UsersDTO;
import com.nomlybackend.nomlybackend.repository.ImagesRepository;
import com.nomlybackend.nomlybackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    public List<UsersDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
//        return users.stream().map(UsersDTO::new).collect(Collectors.toList());
        return users.stream().map(user -> new UsersDTO(user,true)).collect(Collectors.toList());
    }



    public UsersDTO getUserById(int id){
        Users user = usersRepository.findById(id).get();
        return new UsersDTO(user,true);
    }

    public Users getUserEntityById(int id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public boolean deleteUserById(int id){
        if(!usersRepository.findById(id).equals(Optional.empty())){
            usersRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public UsersDTO updateUserById(int id, Map<String,String> body) throws IOException {
        Users current = usersRepository.findById(id).get();


        current.setUsername(body.get("username"));
        current.setEmail(body.get("email"));
        current.setPassword(body.get("password"));
        current.setPreferences(body.get("preferences"));

        String profilePic = body.get("profilePicture");
        if (profilePic != null){
            Images oldImage = current.getImage();
            if (oldImage != null){
                usersRepository.save(current);
                current.setImage(null);
                imagesRepository.delete(oldImage);
            }

            //upload image
            byte[] imageBytes = Base64.getDecoder().decode(profilePic);
            Images image = new Images();
            image.setProfilePicture(imageBytes);
            imagesRepository.save(image);
            current.setImage(image);
        }

        usersRepository.save(current);

        return new UsersDTO(current, true);
    }


    public UsersDTO createUser(Map<String,String> body){
        Users newUser = new Users();
        newUser.setUsername(body.get("username"));
        newUser.setEmail(body.get("email"));
        newUser.setPassword(body.get("password"));
        newUser.setPreferences(body.get("preferences"));

        String profilePic = body.get("profilePicture");
        if (profilePic != null){
            byte[] imageBytes = Base64.getDecoder().decode(profilePic);
            Images image = new Images();
            image.setProfilePicture(imageBytes);
            imagesRepository.save(image);
            newUser.setImage(image);
        }

        return new UsersDTO(usersRepository.save(newUser), true);
    }
}

//
//
//package com.nomlybackend.nomlybackend.service;
//
//import com.nomlybackend.nomlybackend.exception.BadRequestException;
//import com.nomlybackend.nomlybackend.exception.UserNotFoundException;
//import com.nomlybackend.nomlybackend.model.Users;
//import com.nomlybackend.nomlybackend.model.UsersDTO;
//import com.nomlybackend.nomlybackend.repository.UsersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class UsersService {
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//    public List<UsersDTO> getAllUsers() {
//        List<Users> users = usersRepository.findAll();
//        return users.stream().map(UsersDTO::new).collect(Collectors.toList());
//    }
//
//    public UsersDTO getUserById(int id) {
//        Users user = usersRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
//        return new UsersDTO(user);
//    }
//
//    public boolean deleteUserById(int id) {
//        Users user = usersRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
//        usersRepository.delete(user);
//        return true;
//    }
//
//    public UsersDTO updateUserById(int id, Map<String, String> body) {
//        Users current = usersRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
//
//        if (!body.containsKey("username") || !body.containsKey("email")) {
//            throw new BadRequestException("Missing required fields: 'username' or 'email'.");
//        }
//
//        current.setUsername(body.get("username"));
//        current.setEmail(body.get("email"));
//        current.setPassword(body.getOrDefault("password", current.getPassword()));
//        current.setPreferences(body.getOrDefault("preferences", current.getPreferences()));
//
//        usersRepository.save(current);
//        return new UsersDTO(current);
//    }
//
//    public UsersDTO createUser(Map<String, String> body) {
//        if (!body.containsKey("username") || !body.containsKey("email") || !body.containsKey("password")) {
//            throw new BadRequestException("Missing required fields: 'username', 'email', or 'password'.");
//        }
//
//        String username = body.get("username");
//        String email = body.get("email");
//        String password = body.get("password");
//        String preferences = body.getOrDefault("preferences", "No preferences");
//        LocalDateTime now = LocalDateTime.now();
//        Date createdAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
//
//        Users newUser = new Users(username, email, password, preferences, createdAt);
//        return new UsersDTO(usersRepository.save(newUser));
//    }
//}
