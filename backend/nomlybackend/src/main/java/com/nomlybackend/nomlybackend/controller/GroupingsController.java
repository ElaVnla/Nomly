package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.Groupings;
import com.nomlybackend.nomlybackend.model.GroupingsDTO;
import com.nomlybackend.nomlybackend.model.Users;
import com.nomlybackend.nomlybackend.repository.GroupingsRepository;
import com.nomlybackend.nomlybackend.service.GroupingsService;
import org.apache.catalina.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/groupings")

public class GroupingsController {



//    @Autowired
//    GroupingsRepository groupingsRepository;

    @Autowired
    GroupingsService groupingsService;

    @GetMapping("/get-all-groupings")
    public List<GroupingsDTO> getAllGroups(){
//        return groupingsRepository.findAll();
        return groupingsService.getAllGroupings();
    }

    @GetMapping("/get-grouping/{id}")
    public GroupingsDTO getGrouping(@PathVariable("id") int id){
//        return groupingsRepository.findById(id).get();
        return groupingsService.getGroupingById(id);
    }





    @DeleteMapping("/delete-grouping/{id}")
    public boolean deleteRow (@PathVariable("id") int id){
//        if(!groupingsRepository.findById(id).equals(Optional.empty())){
//            groupingsRepository.deleteById(id);
//            return true;
//        }
//        return false;
        return groupingsService.deleteGroupingById(id);
    }


    @PutMapping("/update-grouping/{id}")
    public GroupingsDTO updateGroup(@PathVariable("id") int id, @RequestBody Map<String,String> body) throws IOException {
//        Groupings current = groupingsRepository.findById(id).get();
//        current.setGroupName(body.get("groupName"));
//        groupingsRepository.save(current);
//        return current;
        return groupingsService.updateGroupingById(id, body);

    }


    @PostMapping("/add-grouping")
    public GroupingsDTO createGrouping(@RequestBody Map<String,String> body){

//        String groupName = body.get("groupName");
//        LocalDateTime now = LocalDateTime.now();
//        Date createdAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
//        Groupings newGrouping = new Groupings(groupName,createdAt);
//
//        return groupingsRepository.save(newGrouping);
        return groupingsService.createGrouping(body);
    }

    @GetMapping("/get-grouping-by-code/{code}")
    public GroupingsDTO getGroupingByCode(@PathVariable("code") String code) {
        return groupingsService.getGroupingByCode(code);
    }

}
