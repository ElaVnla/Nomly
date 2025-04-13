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
    private Double latitude;
    private Double longitude;
    private LocalDateTime meetingDateTime;
    private LocalDateTime createdAt;
    private Boolean completed;

    public SessionsDTO() {
    }

    public SessionsDTO(Sessions session) {
        this.sessionId = session.getSessionId();
        this.grouping = null;
        this.sessionName = session.getSessionName();
        this.location = session.getLocation();
        this.latitude = session.getLatitude();
        this.longitude = session.getLongitude();
        this.meetingDateTime = session.getMeetingDateTime();
        this.createdAt = session.getCreatedAt();
        this.completed = session.getCompleted();
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
        this.latitude = session.getLatitude();
        this.longitude = session.getLongitude();
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

    public Double getLatitude() { return latitude;}

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

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
