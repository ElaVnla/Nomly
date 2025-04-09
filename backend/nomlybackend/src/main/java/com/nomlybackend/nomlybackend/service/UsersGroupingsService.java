package com.nomlybackend.nomlybackend.service;

import com.nomlybackend.nomlybackend.model.Groupings;
import com.nomlybackend.nomlybackend.model.Users;
import com.nomlybackend.nomlybackend.model.UsersGroupings;
import com.nomlybackend.nomlybackend.repository.UsersGroupingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersGroupingsService {
    @Autowired
    private UsersGroupingsRepository usersGroupingsRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private GroupingsService groupingsService;



    public UsersGroupings addUserToGrouping(Map<String,String> body){

        int userId = Integer.parseInt(body.get("userId"));
        Users user = usersService.getUserEntityById(userId);
        int groupId = Integer.parseInt(body.get("groupId"));
        Groupings grouping = groupingsService.getGroupEntityById(groupId);
        LocalDateTime joinedAt = LocalDateTime.now();

        return usersGroupingsRepository.save(new UsersGroupings(user, grouping, joinedAt));
    }

    public boolean removeUserFromGrouping(Map<String,String> body){

        int userId = Integer.parseInt(body.get("userId"));
        int groupId = Integer.parseInt(body.get("groupId"));

        Users user = usersService.getUserEntityById(userId);
        Groupings grouping = groupingsService.getGroupEntityById(groupId);
        for (UsersGroupings ug : user.getUserGroupings()){
            if (ug.getGrouping() == grouping){
                int userGroupId = ug.getUserGroupId();
                if(!usersGroupingsRepository.findById(userGroupId).equals(Optional.empty())){

                    System.out.println(userGroupId);
                    user.getUserGroupings().remove(ug);
                    grouping.getUsersGrouping().remove(ug);

                    usersGroupingsRepository.deleteById(userGroupId);
                    return true;
                }
                return false;

            }
        }

        return false;
    }
}
