package com.nomlybackend.nomlybackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class SessionsDTO {


    private int sessionId;
    private GroupingsDTO grouping;
    private String sessionName;
    private String location;
    private String latlong;
    private LocalDateTime meetingDateTime;
    private LocalDateTime createdAt;
    private Boolean completed;

    public SessionsDTO() {
    }

    public SessionsDTO(Sessions session,  boolean includeGroupings) {
        this.sessionId = session.getSessionId();
//        this.grouping = session.getGrouping();
        if (includeGroupings && session.getGrouping() != null) {
            this.grouping = new GroupingsDTO(session.getGrouping(), false);
        }
        else{
            this.grouping = null;
        }
        this.sessionName = session.getSessionName();
        this.location = session.getLocation();
        this.latlong = session.getLatlong();
        this.meetingDateTime = session.getMeetingDateTime();
        this.createdAt = session.getCreatedAt();
        this.completed = session.getCompleted();
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public GroupingsDTO getGrouping() {
        return grouping;
    }

    public void setGrouping(GroupingsDTO grouping) {
        this.grouping = grouping;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public LocalDateTime getMeetingDateTime() {
        return meetingDateTime;
    }

    public void setMeetingDateTime(LocalDateTime meetingDateTime) {
        this.meetingDateTime = meetingDateTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
