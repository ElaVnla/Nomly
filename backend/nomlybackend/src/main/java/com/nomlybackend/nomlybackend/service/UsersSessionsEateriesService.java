package com.nomlybackend.nomlybackend.service;

import com.nomlybackend.nomlybackend.model.*;
import com.nomlybackend.nomlybackend.model.eateries.Eateries;
import com.nomlybackend.nomlybackend.repository.UsersRepository;
import com.nomlybackend.nomlybackend.repository.UsersSessionsEateriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsersSessionsEateriesService {

    @Autowired
    UsersSessionsEateriesRepository usersSessionsEateriesRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UsersService usersService;
    @Autowired
    SessionsService sessionsService;
    @Autowired
    EateriesService eateriesService;

    public UsersSessionsEateriesDTO userVote(Map<String,String> body){
        int userId = Integer.parseInt(body.get("userId"));
        int sessionId = Integer.parseInt(body.get("sessionId"));
        String eateryId = body.get("eateryId");
        boolean liked = Boolean.parseBoolean(body.get("liked"));
        Users user = usersService.getUserEntityById(userId);
        Sessions session = sessionsService.getSessionEntityById(sessionId);
        Eateries eatery = eateriesService.getEateryEntityById(eateryId);
        return new UsersSessionsEateriesDTO(usersSessionsEateriesRepository.save(new UsersSessionsEateries(user,session,eatery, liked)));
    }

    public List<UsersSessionsEateriesDTO> getUsersVotesByEateryId(String eateryId) {
        List<UsersSessionsEateries> usersSessionsEateries = usersSessionsEateriesRepository.findByEateryEateryId(eateryId);
        return usersSessionsEateries.stream().map(userSessionEatery -> new UsersSessionsEateriesDTO(userSessionEatery)).collect(Collectors.toList());
    }

    public List<UsersSessionsEateriesDTO> getUsersVotesBySessionId(int sessionId) {
        List<UsersSessionsEateries> usersSessionsEateries = usersSessionsEateriesRepository.findBySessionSessionId(sessionId);
        return usersSessionsEateries.stream().map(userSessionEatery -> new UsersSessionsEateriesDTO(userSessionEatery)).collect(Collectors.toList());
    }

    public List<UsersDTO> getFinishedUsersBySessionId(Integer sessionId) {
        List<Users> users = usersRepository.findUsersWithAllEateriesInSession(sessionId);
        return users.stream().map(UsersDTO::new).toList();
    }
}
