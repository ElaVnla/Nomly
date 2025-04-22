package com.nomlybackend.nomlybackend.service;


import com.nomlybackend.nomlybackend.model.*;
import com.nomlybackend.nomlybackend.repository.GroupingsRepository;
import com.nomlybackend.nomlybackend.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupingsService {

    @Autowired
    private GroupingsRepository groupingsRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    public List<GroupingsDTO> getAllGroupings() {
        List<Groupings> groupings = groupingsRepository.findAll();
//        return groupings.stream().map(GroupingsDTO::new).collect(Collectors.toList());
        return groupings.stream().map(group -> new GroupingsDTO(group, true)).collect(Collectors.toList());

    }

    public GroupingsDTO getGroupingById(int id ){
        Groupings grouping = groupingsRepository.findById(id).get();
        return new GroupingsDTO(grouping, true);
    }

    public Groupings getGroupEntityById(int id) {
        return groupingsRepository.findById(id).get();
//                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public boolean deleteGroupingById(int id){
        if(!groupingsRepository.findById(id).equals(Optional.empty())){
            groupingsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public GroupingsDTO updateGroupingById(int id, Map<String,String> body) throws IOException {
        Groupings current = groupingsRepository.findById(id).get();


        current.setGroupName(body.get("groupName"));
        String profilePic = body.get("profilePicture");
        if (profilePic != null){
            Images oldImage = current.getImage();
            if (oldImage != null){
                groupingsRepository.save(current);
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

        groupingsRepository.save(current);
        return new GroupingsDTO(current, true);
    }


    public GroupingsDTO createGrouping( Map<String,String> body){
        Groupings newGrouping = new Groupings();
        newGrouping.setGroupName(body.get("groupName"));

        String code = generateRandomCode();
        newGrouping.setGroupCode(code);

        String profilePic = body.get("profilePicture");
        if (profilePic != null){
            byte[] imageBytes = Base64.getDecoder().decode(profilePic);
            Images image = new Images();
            image.setProfilePicture(imageBytes);
            imagesRepository.save(image);
            newGrouping.setImage(image);
        }

        return new GroupingsDTO(groupingsRepository.save(newGrouping), true) ;
    }

    private String generateRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        String code;

        do {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                builder.append(chars.charAt(random.nextInt(chars.length())));
            }
            code = builder.toString();
        } while (groupingsRepository.findByGroupCode(code).isPresent()); // retry if exists

        return code;
    }

    public GroupingsDTO getGroupingByCode(String code) {
        Groupings group = groupingsRepository.findByGroupCode(code)
                .orElseThrow(() -> new RuntimeException("Group not found with code: " + code));

        return new GroupingsDTO(group, true);
    }
}
