package com.nomlybackend.nomlybackend.service;

import com.nomlybackend.nomlybackend.model.Sessions;
import com.nomlybackend.nomlybackend.model.SessionsDTO;
import com.nomlybackend.nomlybackend.model.SessionsEateries;
import com.nomlybackend.nomlybackend.model.SessionsEateriesDTO;
import com.nomlybackend.nomlybackend.model.eateries.Eateries;
import com.nomlybackend.nomlybackend.repository.SessionsEateriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SessionsEateriesService {

    @Autowired
    SessionsEateriesRepository sessionsEateriesRepository;
    @Autowired
    SessionsService sessionsService;

    public List<SessionsEateriesDTO> getAllSessionEateriesByEateryId(String eateryId){
        List<SessionsEateries> sessionsEateries = sessionsEateriesRepository.findByEateryEateryId(eateryId);
        return sessionsEateries.stream().map(sessionEatery -> new SessionsEateriesDTO(sessionEatery)).collect(Collectors.toList());

    }

    public SessionsEateriesDTO addEateryToSession(Integer sessionId, Eateries eatery){
        Sessions session = sessionsService.getSessionEntityById(sessionId);
        SessionsEateries s = new SessionsEateries(session,eatery);
        return new SessionsEateriesDTO(sessionsEateriesRepository.save(s));
    }

//    public boolean updateRanking(String action, String eateryId, String sessionId){
//
//        return true;
//    }
}
