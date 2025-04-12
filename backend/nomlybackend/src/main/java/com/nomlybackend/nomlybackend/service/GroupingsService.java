package com.nomlybackend.nomlybackend.service;


import com.nomlybackend.nomlybackend.model.Groupings;
import com.nomlybackend.nomlybackend.model.GroupingsDTO;
import com.nomlybackend.nomlybackend.model.Users;
import com.nomlybackend.nomlybackend.model.UsersDTO;
import com.nomlybackend.nomlybackend.repository.GroupingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupingsService {

    @Autowired
    private GroupingsRepository groupingsRepository;

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

    public GroupingsDTO updateGroupingById(int id, Map<String,String> body){
        Groupings current = groupingsRepository.findById(id).get();


        current.setGroupName(body.get("groupName"));

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
