package com.nomlybackend.nomlybackend.controller;

import com.nomlybackend.nomlybackend.model.GroupingsDTO;
import com.nomlybackend.nomlybackend.service.GroupingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/groupings")

public class GroupingsController {



    @Autowired
    GroupingsService groupingsService;

    @GetMapping("/get-all-groupings")
    public List<GroupingsDTO> getAllGroups(){
        return groupingsService.getAllGroupings();
    }

    @GetMapping("/get-grouping/{id}")
    public GroupingsDTO getGrouping(@PathVariable("id") int id){
        return groupingsService.getGroupingById(id);
    }





    @DeleteMapping("/delete-grouping/{id}")
    public boolean deleteRow (@PathVariable("id") int id){
        return groupingsService.deleteGroupingById(id);
    }


    @PutMapping("/update-grouping/{id}")
    public GroupingsDTO updateGroup(@PathVariable("id") int id, @RequestBody Map<String,String> body) throws IOException {
        return groupingsService.updateGroupingById(id, body);

    }


    @PostMapping("/add-grouping")
    public GroupingsDTO createGrouping(@RequestBody Map<String,String> body){
        return groupingsService.createGrouping(body);
    }

    @GetMapping("/get-grouping-by-code/{code}")
    public GroupingsDTO getGroupingByCode(@PathVariable("code") String code) {
        return groupingsService.getGroupingByCode(code);
    }

}
