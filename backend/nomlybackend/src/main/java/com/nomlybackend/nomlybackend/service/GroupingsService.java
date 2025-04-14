package com.nomlybackend.nomlybackend.service;


import com.nomlybackend.nomlybackend.model.*;
import com.nomlybackend.nomlybackend.repository.GroupingsRepository;
import com.nomlybackend.nomlybackend.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
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
        // TODO make groupings and users subclass of profile to remove code reuse
        Images oldImage = current.getImage();
        if (oldImage != null){
            groupingsRepository.save(current);
            current.setImage(null);
            imagesRepository.delete(oldImage);
        }

        byte[] imageBytes = Base64.getDecoder().decode(body.get("profilePicture"));
        Images image = new Images();
        image.setProfilePicture(imageBytes);
        imagesRepository.save(image);
        current.setImage(image);

        groupingsRepository.save(current);
        return new GroupingsDTO(current, true);
    }


    public GroupingsDTO createGrouping( Map<String,String> body){
        String groupName = body.get("groupName");
        LocalDateTime now = LocalDateTime.now();
//        Date createdAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime createdAt = LocalDateTime.now();
        String code = generateRandomCode(); //TODO Check code duplicate
        Groupings newGrouping = new Groupings(groupName,createdAt, code);

        return new GroupingsDTO(groupingsRepository.save(newGrouping), true) ;
    }

    private String generateRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }

    public GroupingsDTO getGroupingByCode(String code) {
        Groupings group = groupingsRepository.findByGroupCode(code);
        if (group == null) {
            throw new RuntimeException("Group not found with code: " + code);
        }
        return new GroupingsDTO(group, true);
    }

}
