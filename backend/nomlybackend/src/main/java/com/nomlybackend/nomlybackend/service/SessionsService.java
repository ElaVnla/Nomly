package com.nomlybackend.nomlybackend.service;


import com.nomlybackend.nomlybackend.model.Groupings;
import com.nomlybackend.nomlybackend.model.Sessions;
import com.nomlybackend.nomlybackend.model.SessionsDTO;
import com.nomlybackend.nomlybackend.repository.GroupingsRepository;
import com.nomlybackend.nomlybackend.repository.SessionsRepository;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionsService {
    @Autowired
    SessionsRepository sessionsRepository;
    @Autowired
    GroupingsService groupingsService;

    public List<SessionsDTO> getAllSessions(){
        List<Sessions> sessions = sessionsRepository.findAll();
        return sessions.stream().map(SessionsDTO::new).collect(Collectors.toList());
    }

    public SessionsDTO getSessionById(int id){
        return new SessionsDTO(sessionsRepository.findById(id).get());
    }

    public boolean deleteSessionById(int id){
        if(!sessionsRepository.findById(id).equals(Optional.empty())){
            sessionsRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public SessionsDTO updateSessionById(int id, Map<String,String> body) {
        Sessions current = sessionsRepository.findById(id).get();


        current.setLocation(body.get("location"));
        current.setLatlong(body.get("latlong"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        current.setMeetingDateTime(LocalDateTime.parse(body.get("meetingDateTime"), formatter));

        sessionsRepository.save(current);

        return new SessionsDTO(current);
    }

    public SessionsDTO createSession( Map<String,String> body){

        int groupId = Integer.parseInt(body.get("groupId"));
        Groupings grouping = groupingsService.getGroupEntityById(groupId);
        String location = body.get("location");
        String latlong = body.get("latlong");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime meetingDateTime = LocalDateTime.parse(body.get("meetingDateTime"), formatter);
        LocalDateTime createdAt = LocalDateTime.now();
        Boolean completed = false;

        Sessions newSession = new Sessions(grouping,location,latlong,meetingDateTime,createdAt,completed);

        return new SessionsDTO(sessionsRepository.save(newSession));

    }

    public SessionsDTO sessionCompleted (int id){
        Sessions current  = sessionsRepository.findById(id).get();

        current.setCompleted(true);

        return new SessionsDTO(sessionsRepository.save(current));

    }
}
